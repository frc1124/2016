package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The shooter subsystem; extends PIDSubsystem. This PID controls rate, not distance.
 */
public class ShooterPID extends PIDSubsystem implements Safe {
	/** TODO tune these */
	private final static double P = 0.00001;
	private final static double I = 0.0;
	private final static double D = 0.0;
	
	public final double SETPOINT_TOLERANCE = 2.0;
	
	private CANTalon shooter;
	
	private Timer safetyTimer = new Timer();
	private boolean timerFirstCall = true;
	private final double TIME_DELAY = 0.2;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	private double rate_threshold = 0.2;

    // Initialize your subsystem here
    public ShooterPID() {
    	super("ShooterPID", P, I, D);
    	
    	shooter = new CANTalon(Robot.configIO.getIntVal("shooter"));
    	
    	setSetpoint(0);
    }
    
    public void initDefaultCommand() {}

    /**
     * Stops the PID control and sets output to 0.
     */
    public void stop(){
    	disable();
    	setSetpoint(0);
    	
    	shooter.set(0);
    }
    
    /**
     * Resumes the PID control.
     */
    public void resume(){
    	enable();
    }
    
    /**
     * Run the stop() method before running this. Run resume() when done.
     * @param speed the speed between -1.0 and 1.0
     */
    public void manual(double speed){
    	shooter.set(speed);
    }
    
    /* Encoder Functions */
    
    public double getRate(){
    	return shooter.getEncVelocity();
    }

    /* PID Control */
    
    protected double returnPIDInput() {
    	return getRate();
    }
    
    protected void usePIDOutput(double output) {
        if(safetyEnabled){
        	shooter.set(safeOutput(output));
        }else{
        	shooter.set(output);
        }
    }

    /* Safeties */
    
	public void enableSafety() {
		safetyEnabled = true;
	}

	public void disableSafety() {
		safetyEnabled = false;
	}

	public boolean isSafetyEnabled() {
		return safetyEnabled;
	}

	public boolean isSafetyTripped() {
		return safetyTripped;
	}

	public void setRateCutoffThreshold(double threshold) {
		rate_threshold = threshold;
	}

	public double getRateCutoffThreshold() {
		return rate_threshold;
	}

	public double safeOutput(double output) {
		double safeOutput = 0;
		
		if(timerFirstCall){
			safetyTimer.start();
			
			timerFirstCall = false;
		}
		
		// rate safeties
		if(Math.abs(getRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, SafetyError.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.Shooter, SafetyError.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && getRate() == 0 && safetyTimer.get() >= TIME_DELAY){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, SafetyError.NoRateDisconnection);
		}else if(safetyTimer.get() >= TIME_DELAY){
			safetyTimer.reset();
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.Shooter, SafetyError.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
