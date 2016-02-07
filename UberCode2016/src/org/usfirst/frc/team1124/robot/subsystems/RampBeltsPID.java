package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.ramp.RampHoldPosition;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The belts inside the robot to store the ball and feed it to the shooter. Uses a rate based PID.
 */
public class RampBeltsPID extends PIDSubsystem implements Safe {
	
	/** TODO tune these */
	public final static double P = 0.00001;
	public final static double I = 0.0;
	public final static double D = 0.0;
	
	private CANTalon talon;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	/** TODO tune this */
	private double rate_threshold = 0.2;
	
	/** TODO tune these */
	private double FEED_TO_INTAKE_RATE = -100;
	private double FEED_TO_SHOOTER_RATE = 100;

    public RampBeltsPID() {
    	super("RampBelts", P, I, D);
    	
    	talon = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));

		setSetpoint(0);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new RampHoldPosition());
    }
    
    /** Feed a ball back to the intake to score a low goal */
    public void feedToIntake(){
    	this.getPIDController().reset();
    	enable();
    	setSetpoint(FEED_TO_INTAKE_RATE);
    }
    
    /** Feed a ball to the shooter to score a high goal */
    public void feedToShooter(){
    	this.getPIDController().reset();
    	enable();
    	setSetpoint(FEED_TO_SHOOTER_RATE);
    }
    
    /** Hold the belts at a rate of 0 */
    public void holdPosition(){
    	this.getPIDController().reset();
    	enable();
    	setSetpoint(0);
    }
    
    public void stop(){
    	// not used unless we want to disable the hold position
    	disable();
    	setSetpoint(0);
    	
    	talon.set(0);
    }
    
    /* Encoder Functions */
    
    public double getEncoderRate(){
    	return talon.getEncVelocity();
    }
    
    /* PID Control */
    
    protected double returnPIDInput() {
        return getEncoderRate();
    }
    
    protected void usePIDOutput(double output) {
        if(safetyEnabled){
        	talon.set(safeOutput(output));
        }else{
        	talon.set(output);
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
		double safeOutput = output;
		
		// rate safeties
		if(Math.abs(talon.getEncVelocity()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.RampBelts, SafetyError.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.RampBelts, SafetyError.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && talon.getEncVelocity() == 0){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.RampBelts, SafetyError.NoRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.RampBelts, SafetyError.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
