package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem to manage the intake belts and the wheels on the arm
 */
public class Intake extends Subsystem {
    
	private CANTalon belt_motor;
	private CANTalon arm_wheels_motor;
	
	private DigitalInput light_sensor;
	
	/** TODO tune these */
	private final int INTAKE_SPEED = 1;
	private final int LOW_GOAL_SPEED = -1;
	
	public Intake(){
		super("Intake");
		
		belt_motor = new CANTalon(Robot.configIO.getIntVal("intake_belts"));
		arm_wheels_motor = new CANTalon(Robot.configIO.getIntVal("arm_intake_wheels"));
		
		light_sensor = new DigitalInput(Robot.configIO.getIntVal("intake_belts_light_sensor"));
	}
	
    public void initDefaultCommand() {}
    
    /** Already inverted, since roboRIO uses pull-up resistors on its DIO ports*/
    public boolean getBallDetected(){
    	return !light_sensor.get();
    }
    
    /** Intake a ball */
    public void intake(){
    	belt_motor.set(INTAKE_SPEED);
    	arm_wheels_motor.set(INTAKE_SPEED);
    }
    
    /** Spit the ball back out */
    public void spit(){
    	belt_motor.set(LOW_GOAL_SPEED);
    	arm_wheels_motor.set(LOW_GOAL_SPEED);

    }
    
    /** Stop the belts */
    public void stop(){
    	belt_motor.set(0);
    	arm_wheels_motor.set(0);
    }
    
    /** Manual control of the belts 
     *  @param speed The speed to run the belts (-1.0 to 1.0) 
     */
    public void manual(int speed){
    	belt_motor.set(speed);
    	arm_wheels_motor.set(speed);
    }
}

