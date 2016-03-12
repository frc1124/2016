package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1124.robot.tools.Safe;

/**
 * The linear actuator on the arm used to actuate the arm.
 */
public class ArmActuator extends Subsystem implements Safe {
	private CANTalon actuator;
	private Encoder encoder;
	
	private Timer safetyTimer = new Timer();
	private boolean timerFirstCall = true;
	private final double TIME_DELAY = 0.2;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	/** TODO tune this */
	private double rate_threshold = 1.0;
	
	/** TODO tune these */
	//private final double MAX_UP = 1;
	//private final double MAX_DOWN = 0;
	
	private DigitalInput limit_switch_back;
	private DigitalInput limit_switch_forward;
	
	public ArmActuator() {
		super("ArmActuators");
		
		actuator = new CANTalon(Robot.configIO.getIntVal("arm_actuator"));
		
		int a_channel = Robot.configIO.getIntVal("arm_encoder_a");
		int b_channel = Robot.configIO.getIntVal("arm_encoder_b");
		
		encoder = new Encoder(a_channel, b_channel);
		
		limit_switch_back = new DigitalInput(Robot.configIO.getIntVal("arm_actuator_limit_back"));
		limit_switch_forward = new DigitalInput(Robot.configIO.getIntVal("arm_actuator_limit_forward"));
		
		enableSafety();
	}
	
    public void initDefaultCommand() {}
    
    public void manual(double outputValue){
		if(isSafetyEnabled()){
			actuator.set(safeOutput(-outputValue));
		}else{
			actuator.set(0);
		}
    }
    
    public void stop(){
    	actuator.set(0);
    }
    
    /* Limit Switches */
    
    /**
     * Get the arm's limit switches.
     * @return a 1 dimensional boolean array of {back, forward} limit switches (in that order).
     */
    public boolean[] getLimitSwitchStates(){
    	boolean[] switches = {
    			limit_switch_back.get(),
    			limit_switch_forward.get(),
    		};
    	
    	return switches;
    }
	
	//measures in changer per millisecond
	public double getRate(){
		return encoder.getRate();
	}
	
	/* Encoders */
	
	public double getDistance(){
		return encoder.getDistance();
	}
	
	/* Safety Code */
	
	public void enableSafety() {
		safetyEnabled = true;
	}
	
	/** Manual control is forbidden for the arm.
	 * @deprecated You may not disable the arm actuator's safety. 
	 */
	public void disableSafety() {}

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
	
	public double safeOutput(double output){
		double safeOutput = output;
		
		if(timerFirstCall){
			safetyTimer.start();
			
			timerFirstCall = false;
		}
		
		// directional safety
		if(limit_switch_forward.get() && output > 0 || limit_switch_back.get() && output < 0){
			// trying to go too far up, so only allow going down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.LimitSwitchDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, SafetyError.LimitSwitchDirection);
		}
		
		/*
		if(getDistance() >= MAX_UP && output > 0){	//Do we need a potentiometer threshold for max if we already have a limit switch?
			// trying to go to far up
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.EncoderDirection);
		}else if(getDistance() <= MAX_DOWN && output < 0){	//If we have a limit switch for max, why not min? Mechanical decision?
			// trying to go to far down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.EncoderDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, SafetyError.EncoderDirection);
		}
		*/
		
		if(Math.abs(output) > getRateCutoffThreshold() && getRate() == 0 && safetyTimer.get() >= TIME_DELAY){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.NoRateDisconnection);
		}else if(safetyTimer.get() >= TIME_DELAY){
			safetyTimer.reset();
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, SafetyError.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}