package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.belts.RampHoldPosition;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.Error;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The belts inside the robot to store the ball and feed it to the shooter
 */
public class RampBeltsPID extends PIDSubsystem implements Safe {
	
	public final static double P = 1;
	public final static double I = 0.01;
	public final static double D = 0;
	
	private CANTalon talon;
	private Encoder encoder;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	private double rate_threshold = 0.2;

    public RampBeltsPID() {
    	super("RampBelts", P, I, D);
    	
    	talon = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));
    	
    	int port_a = Robot.configIO.getIntVal("ramp_talon_enc_a");
		int port_b = Robot.configIO.getIntVal("ramp_talon_enc_b");
		
		encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);

		setSetpoint(0);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new RampHoldPosition());
    }
    
    public void stop(){
    	// not used unless we want to disable the hold position
    	disable();
    	setSetpoint(0);
    	
    	talon.set(0);
    }
    
    /* Encoder Functions */
    
    public double getEncoderRate(){
    	return encoder.getRate();
    }
    
    /* PID Control */
    
    protected double returnPIDInput() {
        return encoder.getRate();
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
		double safeOutput = 0;
		
		// rate safeties
		if(Math.abs(encoder.getRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.RampBelts, Error.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.RampBelts, Error.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && encoder.getRate() == 0){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.RampBelts, Error.NoRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.RampBelts, Error.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
