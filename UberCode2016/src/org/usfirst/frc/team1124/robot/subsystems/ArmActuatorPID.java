package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The linear actuator on the arm used to actuate the arm
 */
public class ArmActuatorPID extends PIDSubsystem {
	
	private final static double P = 0;
	private final static double I = 0;
	private final static double D = 0;
	
	private Talon actuator;
	private Encoder encoder;
	
	public ArmActuatorPID(){
		super("ArmActuators", P, I, D);
		
		actuator = new Talon(Robot.configIO.getIntVal("arm_actuator"));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
}