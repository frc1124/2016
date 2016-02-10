package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The belts inside the robot to store the ball and feed it to the shooter.
 */
public class RampBelts extends Subsystem {
	
	private CANTalon ramp_belts;

	/** TODO tune these */
	private final double FEED_TO_INTAKE = -1.0;
	private final double FEED_TO_SHOOTER = 1.0;

    public RampBelts() {
    	super("RampBelts");
    	
    	ramp_belts = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));
    }
    
    public void initDefaultCommand() {
        //setDefaultCommand(new RampHoldPosition());
    }
    
    public void feedToShooter(){
    	ramp_belts.set(FEED_TO_SHOOTER);
    }
    
    public void spit(){
    	ramp_belts.set(FEED_TO_INTAKE);
    }
    
    public void stop(){
    	ramp_belts.set(0);
    }
}
