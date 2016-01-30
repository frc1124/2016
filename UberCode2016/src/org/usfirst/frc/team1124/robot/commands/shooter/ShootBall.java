package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Shoots a ball at a specific rate (using PID)
 */
public class ShootBall extends Command {
	
    public ShootBall(double setpoint){
    	requires(Robot.shooter_pid);
    	
    	Robot.shooter_pid.setSetpoint(setpoint);
    	
    	setInterruptible(true);
    }

    protected void initialize(){
    	Robot.shooter_pid.enable();
    }

    protected void execute() {}

    protected boolean isFinished(){
    	return Robot.shooter_pid.onTarget();
    }

    protected void end(){
    	Robot.shooter_pid.stop();
    }

    protected void interrupted(){
    	end();
    }
}
