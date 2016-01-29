package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem to manage the intake belts
 */
public class IntakeBelts extends Subsystem {
    
	private Talon belt_motor;
	private Talon arm_belts_motor;
	
	private DigitalInput light_sensor;
	
	private final int INTAKE_SPEED = 1;
	private final int LOW_GOAL_SPEED = -1;
	private final int SHOOT_SPEED = 1; // belts to shooter
	
	public IntakeBelts(){
		super("IntakeBelts");
		
		belt_motor = new Talon(Robot.configIO.getIntVal("intake_belts"));
		arm_belts_motor = new Talon(Robot.configIO.getIntVal("arm_intake_belts"));
		
		light_sensor = new DigitalInput(Robot.configIO.getIntVal("intake_belts_light_sensor"));
	}
	
    public void initDefaultCommand() {
        //setDefaultCommand(new Command());
    }
    
    public boolean getSensorState(){
    	return light_sensor.get();
    }
    
    /** Intake a ball */
    public void intake(){
    	belt_motor.set(INTAKE_SPEED);
    	arm_belts_motor.set(INTAKE_SPEED);
    }
    
    /** Shoot a ball */
    public void shoot(){
    	belt_motor.set(SHOOT_SPEED);
    	arm_belts_motor.set(SHOOT_SPEED);
    }
    
    /** Spit the ball back out */
    public void spit(){
    	belt_motor.set(LOW_GOAL_SPEED);
    	arm_belts_motor.set(LOW_GOAL_SPEED);

    }
    
    /** Stop the belts */
    public void stop(){
    	belt_motor.set(0);
    	arm_belts_motor.set(0);

    }
    
    /** Manual control of the belts 
     *  @param speed The speed to run the belts (-1.0 to 1.0) 
     */
    public void manual(int speed){
    	belt_motor.set(speed);
    }
}

