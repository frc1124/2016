package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.arm.ArmHoldPosition;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The linear actuator on the arm used to actuate the arm; implements PID subsystem. PID control for distance.
 */
public class ArmActuatorPID extends PIDSubsystem {
	
	public final static double P = 1;
	public final static double I = 0.01;
	public final static double D = 0;
	
	private CANTalon actuator;
	private Encoder encoder;
	
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
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ArmHoldPosition());
    }

	protected double returnPIDInput() {
		return encoder.getDistance();
	}

	protected void usePIDOutput(double output) {
		actuator.set(output);
	}
}