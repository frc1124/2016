package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootBall extends Command {

	// take in the needed setpoint
	
	// is ball loaded
	// spin up to speed
	// feed ball
	
	private double setpoint = 1;
	
    public ShootBall(double setpoint)
    {
    	requires(Robot.shooter_pid);
    	requires(Robot.intake_belts);
    	this.setpoint = setpoint; // takes in a value that comes from the vision calculations
    	setInterruptible(true);
    }

    protected void initialize()
    {
    	if (Robot.intake_belts.getSensorState()) // ball loaded already
    	{
    		Robot.shooter_pid.enable();
    		Robot.shooter_pid.setSetpoint(setpoint);
    		
    	}
    }

    protected void execute()
    {
    	
    }

    protected boolean isFinished()
    {
    	return Robot.shooter_pid.onTarget();
    }

    protected void end()
    {
    	Robot.intake_belts.shoot();
    	setTimeout(2); // change this to stop running the belts when needed
    	Robot.intake_belts.stop();
    	Robot.shooter_pid.disable();
    }

    protected void interrupted()
    {
    	Robot.shooter_pid.disable();
    }
}
