package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem to manage the intake belts
 */
public class IntakeBelts extends Subsystem {
    
	private Talon motor;
	
	private final int INTAKE_SPEED = 1;
	private final int LOW_GOAL_SPEED = -1;
	
	public IntakeBelts(){
		super("IntakeBelts");
		
		motor = new Talon(Robot.configIO.getIntVal("intake_belts"));
	}
	
    public void initDefaultCommand() {
        //setDefaultCommand(new Command());
    }
    
    /** Intake a ball */
    public void intake(){
    	motor.set(INTAKE_SPEED);
    }
    
    /** Spit the ball back out */
    public void spit(){
    	motor.set(LOW_GOAL_SPEED);
    }
    
    /** Stop the belts */
    public void stop(){
    	motor.set(0);
    }
    
    /** Manual control of the belts 
     *  @param speed The speed to run the belts (-1.0 to 1.0) 
     */
    public void manual(int speed){
    	motor.set(speed);
    }
}

