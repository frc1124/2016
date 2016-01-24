package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.belts.LoadIntoShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class ShootBall extends Command {
	
	private double setpoint = 1;
	
    public ShootBall(double setpoint)
    {
    	requires(Robot.shooter_pid);
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

    protected void execute() {}

    protected boolean isFinished()
    {
    	return Robot.shooter_pid.onTarget();
    }

    protected void end()
    {
    	Scheduler.getInstance().add(new LoadIntoShooter());
    }

    protected void interrupted()
    {
    	Robot.shooter_pid.disable();
    }
}
