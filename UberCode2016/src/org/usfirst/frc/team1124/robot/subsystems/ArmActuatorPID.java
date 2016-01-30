package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.arm.ArmHoldPosition;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.SafetySubsystem;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.Error;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

/**
 * The linear actuator on the arm used to actuate the arm; implements PID subsystem. PID control for distance.
 * Positive = Arm Up, Negative = Arm Down
 */
public class ArmActuatorPID extends PIDSubsystem implements Safe {
	
	public final static double P = 1;
	public final static double I = 0.01;
	public final static double D = 0;
	
	private CANTalon actuator;
	private Encoder encoder;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	private double rate_threshold = 0.2;
	
	private final double MAX_UP = 0;
	private final double MAX_DOWN = -100; // PLS CHANGE THIS!!!!!!!!!!!
	
	private DigitalInput limit_switch;
	
	public ArmActuatorPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
		super("ArmActuators", P, I, D);
		
		actuator = new CANTalon(Robot.configIO.getIntVal("arm_actuator"));
		
		int port_a = Robot.configIO.getIntVal("arm_actuator_enc_a");
		int port_b = Robot.configIO.getIntVal("arm_actuator_enc_b");
		
		encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);
		
		limit_switch = new DigitalInput(Robot.configIO.getIntVal("arm_actuator_limit"));
		
		// will be the default position at start
		setSetpoint(0);
		
		enableSafety();
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ArmHoldPosition());
    }
    
    /* PID Control */
    
	protected double returnPIDInput() {
		return encoder.getDistance();
	}

	protected void usePIDOutput(double output) {
		actuator.set(output);
	}
	
	/* Encoder Functions */
	
	public void homeEncoder(){
		encoder.reset();
	}
	
	/* Safety Code */
	
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
	
	public void setRateCutoffThreshold(double threshold){
		rate_threshold = threshold;
	}

	public double getRateCutoffThreshold() {
		return rate_threshold;
	}
	
	public double safeOutput(double output){
		double safeOutput = 0;
		
		// directional safety
		if(limit_switch.get() && output > 0){
			// trying to go too far up, so only allow going down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, Error.LimitSwitchDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, Error.LimitSwitchDirection);
		}

		if(encoder.getDistance() >= MAX_UP && output > 0){
			// trying to go to far up
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, Error.EncoderDirection);
		}else if(encoder.getDistance() <= MAX_DOWN && output < 0){
			// trying to go to far down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, Error.EncoderDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, Error.EncoderDirection);
		}
		
		// rate safeties
		if(Math.abs(encoder.getRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, Error.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, Error.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && encoder.getRate() == 0){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, Error.NoRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, Error.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}