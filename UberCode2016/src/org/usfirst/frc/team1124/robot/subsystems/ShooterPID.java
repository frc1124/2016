package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The shooter subsystem; extends PIDSubsystem. This PID controls rate, not distance.
 */
public class ShooterPID extends PIDSubsystem {
	
	private final static double P = 1;
	private final static double I = 0.01;
	private final static double D = 0;
	
	private Talon actuator;
	private Encoder encoder;

    // Initialize your subsystem here
    public ShooterPID() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super("ShooterPID", P, I, D);
    	
    	actuator = new Talon(Robot.configIO.getIntVal("shooter"));
    	
    	int port_a = Robot.configIO.getIntVal("shooter_enc_a");
    	int port_b = Robot.configIO.getIntVal("shooter_enc_b");
    	
    	encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
