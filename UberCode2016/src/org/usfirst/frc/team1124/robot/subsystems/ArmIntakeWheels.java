package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem to manage the wheels on the arm
 */
public class ArmIntakeWheels extends Subsystem {
	private CANTalon arm_wheels_motor;
	
	/** TODO tune these */
	private final double INTAKE_SPEED = -1;
	private final double LOW_GOAL_SPEED = 1;
	
	public ArmIntakeWheels(){
		super("ArmIntakeWheels");
		
		arm_wheels_motor = new CANTalon(Robot.configIO.getIntVal("arm_intake_wheels"));
	}
	
    public void initDefaultCommand() {}
    
    /** Intake a ball */
    public void intake(){
    	arm_wheels_motor.set(INTAKE_SPEED);
    }
    
    /** Spit the ball back out */
    public void spit(){
    	arm_wheels_motor.set(LOW_GOAL_SPEED);
    }
    
    /** Manual control of the wheels
     *  @param speed The speed to run the wheels (-1.0 to 1.0) 
     */
    public void manual(double speed){
    	arm_wheels_motor.set(speed);
    }
    
    /** Stop the belts */
    public void stop(){
    	arm_wheels_motor.set(0);
    }
}

