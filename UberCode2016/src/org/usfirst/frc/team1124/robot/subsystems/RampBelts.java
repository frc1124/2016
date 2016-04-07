package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The belts inside the robot to store the ball and feed it to the shooter.
 */
public class RampBelts extends Subsystem {
	private CANTalon ramp_belts;
	
	private DigitalInput light_sensor;

	private final double FEED_TO_INTAKE = -1;
	private final double INTAKE_SPEED = 0.8;
	private final double FEED_TO_SHOOTER = 1;
	
	private int ballCount = 0;

    public RampBelts() {
    	super("RampBelts");
    	
    	ramp_belts = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));
		
		light_sensor = new DigitalInput(Robot.configIO.getIntVal("intake_belts_light_sensor"));
    }
    
    public void initDefaultCommand() {}
    
    // keep track of balls in robot
    
    public void addBall(){
    	ballCount++;
    }
    
    public void removeBall(){
    	ballCount--;
    }
    
    public int getBallCount(){
    	return ballCount;
    }
    
    /** True is detected */
    public boolean getBallDetected(){
    	return light_sensor.get();
    }
    
    /** Intake a ball */
    public void intake(){
    	ramp_belts.set(INTAKE_SPEED);
    }
    
    public void feedToShooter(){
    	ramp_belts.set(FEED_TO_SHOOTER);
    }
    
    public void spit(){
    	ramp_belts.set(FEED_TO_INTAKE);
    }
    
    /** Manual control of the belts 
     *  @param speed The speed to run the belts (-1.0 to 1.0) 
     */
    public void manual(double speed){
    	ramp_belts.set(speed);
    }
    
    public void stop(){
    	ramp_belts.set(0);
    }
}
