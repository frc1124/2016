package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The linear actuator on the arm used to actuate the arm; implements PID subsystem. PID control for distance.
 */
public class ArmActuatorPID extends PIDSubsystem {
	
	private final static double P = 1;
	private final static double I = 0.01;
	private final static double D = 0;
	
	private Talon actuator;
	private Encoder encoder;
	
	private DigitalInput limit_switch;
	
	private double setpoint; // used for arm presets (full up, full down, etc.)
	
	public ArmActuatorPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
		super("ArmActuators", P, I, D);
		
		actuator = new Talon(Robot.configIO.getIntVal("arm_actuator"));
		
		int port_a = Robot.configIO.getIntVal("arm_actuator_enc_a");
		int port_b = Robot.configIO.getIntVal("arm_actuator_enc_b");
		
		encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);
		
		limit_switch = new DigitalInput(Robot.configIO.getIntVal("arm_actuator_limit"));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	protected double returnPIDInput() {
		return encoder.getRate();
	}

	protected void usePIDOutput(double output) {
		actuator.pidWrite(output);
	}
}