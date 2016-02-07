package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.Error;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The shooter subsystem; extends PIDSubsystem. This PID controls rate, not distance.
 */
public class ShooterPID extends PIDSubsystem implements Safe {

	private final static double P = 1;
	private final static double I = 0.01;
	private final static double D = 0;
	
	public final double SETPOINT_TOLERANCE = 2.0;
	
	private CANTalon shooter;
	private Encoder encoder;
	
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
		
		// rate safeties
		if(Math.abs(encoder.getRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, Error.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.Shooter, Error.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && encoder.getRate() == 0){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.Shooter, Error.NoRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.Shooter, Error.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
