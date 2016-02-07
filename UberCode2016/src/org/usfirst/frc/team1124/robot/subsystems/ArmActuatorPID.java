package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.arm.ArmHoldPosition;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

/**
 * The linear actuator on the arm used to actuate the arm; implements PID subsystem. PID control for distance. </br>
 * Positive = Arm Up, Negative = Arm Down
 */
public class ArmActuatorPID extends PIDSubsystem implements Safe {
	
	/** TODO tune these */
	public final static double P = 0.00001;
	public final static double I = 0.0;
	public final static double D = 0.0;
	
	private Timer timer;
	private double lastVoltage;
	private double lastTime;
	private double rate;
	
	private CANTalon actuator;
	private AnalogPotentiometer potentiometer;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	
	/** TODO tune these */
	private final double MAX_UP = 1;
	private final double MAX_DOWN = 0;
	
	private DigitalInput limit_switch;
	
	public ArmActuatorPID() {
		super("ArmActuators", P, I, D);
		
		timer = new Timer();
		timer.start();
		lastTime = timer.get();
		
		actuator = new CANTalon(Robot.configIO.getIntVal("arm_actuator"));
		
		potentiometer = new AnalogPotentiometer(Robot.configIO.getIntVal("arm_potentiometer"), 1, 0); // 0-1 range, no offset
		lastVoltage = potentiometer.get();
		
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
		return potentiometer.get();
	}

	protected void usePIDOutput(double output) {
		lastVoltage = potentiometer.get();
		lastTime = timer.get();
		
		if(isSafetyEnabled()){
			actuator.set(safeOutput(output));
		}else{
			actuator.set(output);
		}
	}
	
	//measures in changer per millisecond
	public double getRateChange(){
		double voltChange = potentiometer.get() - lastVoltage;
		double timeChange = timer.get() - lastTime;
		return voltChange / timeChange;
	}
	
	/* Safety Code */
	
	public void enableSafety() {
		safetyEnabled = true;
	}
	
	/** Manual control is forbidden for the arm.
	 * @deprecated You may not disable te arm actuator's safety. 
	 */
	public void disableSafety() {}

	public boolean isSafetyEnabled() {
		return safetyEnabled;
	}

	public boolean isSafetyTripped() {
		return safetyTripped;
	}
	
	/** 
	 * This subsystem does not have rate control.
	 * @deprecated cannot use rate safeties with a potentiometer
	 */
	public void setRateCutoffThreshold(double threshold) {}

	/** 
	 * This subsystem does not have rate control.
	 * @deprecated cannot use rate safeties with a potentiometer
	 */
	public double getRateCutoffThreshold() {
		return 0.0;
	}
	
	public double safeOutput(double output){
		double safeOutput = 0;
		
		// directional safety
		if(limit_switch.get() && output > 0){
			// trying to go too far up, so only allow going down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.LimitSwitchDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, SafetyError.LimitSwitchDirection);
		}

		if(potentiometer.get() >= MAX_UP && output > 0){
			// trying to go to far up
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.PotentiometerDirection);
		}else if(potentiometer.get() <= MAX_DOWN && output < 0){
			// trying to go to far down
			safeOutput = 0;
			
			SafetyErrorLogger.log(SafetySubsystem.ArmActuator, SafetyError.PotentiometerDirection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.ArmActuator, SafetyError.PotentiometerDirection);
		}
		
		return safeOutput;
	}
}