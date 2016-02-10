package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
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
	private Encoder encoder;
	
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
    	
    	int port_a = Robot.configIO.getIntVal("shooter_enc_a");
    	int port_b = Robot.configIO.getIntVal("shooter_enc_b");
    	
    	encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);
    	
    	setSetpoint(0);
    }
    
    public void initDefaultCommand() {}
    
    public void stop(){
    	disable();
    	setSetpoint(0);
    	
    	shooter.set(0);
    }
    
    /* Encoder Functions */
    
    public double getRate(){
    	return encoder.getRate();
    }

    /* PID Control */
    
    protected double returnPIDInput() {
    	return encoder.getRate();
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
		if(Math.abs(encoder.getRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, SafetyError.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.Shooter, SafetyError.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && encoder.getRate() == 0 && safetyTimer.get() >= TIME_DELAY){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, SafetyError.NoRateDisconnection);
		}else if(safetyTimer.get() >= TIME_DELAY){
			safetyTimer.reset();
			//I think this system of just resetting the safety timer when rate is not zero and the timer
			//has gone over might be flawed. This is only called when a new setpoint is set (as far as I understand),
			//so this safety will never fire as intended. What I think you want is to check whether the set rate is
			//nonzero and the encoder rate is zero very frequently, and if this is the case, start (or continue) a
			//timer. Once that timer reaches a certain time, then stop the motor. As is, if you set a setpoint once,
			//this safety can't trigger even if the motor doesn't move.
			
			//In fact, I think you might want to accumulate speed setpoint over all time periods when encoder rate
			//is 0 and setpoint is nonzero, and trigger the safety once the accumulated speed setpoints get over
			//a certain threshold. This way, if you tell the motor to go really fast, it will trigger the safety 
			//quicker than if you just tell it to move slowly. I think this is good, because I think it is easier
			//to kill a motor with a high speed setpoint and no movement than with a low one, but I'm not sure...
			
			//Of course, this safety doesn't even really matter for the shooter wheel--I can't imagine it'll ever
			//trip it :P
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
