package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Shoots a ball at a specific rate (using PID)
 */
public class BringShooterToSpeed extends Command {
	
	private double setpoint = 0;
	
    public BringShooterToSpeed(){	//shouln't this take the speed setpoint as an argument? Our speed is going to need to vary, afaik
    	requires(Robot.shooter_pid);
    	
    	setpoint = Robot.camera_system.getRateForShooterToScore();
    	
    	Robot.shooter_pid.setSetpoint(setpoint);
    	
    	setInterruptible(true);
    }

    protected void initialize(){
    	Robot.shooter_pid.enable();
    }

    protected void execute() {}

    protected boolean isFinished(){
    	return Math.abs(Robot.shooter_pid.getRate() - setpoint) >= Robot.shooter_pid.SETPOINT_TOLERANCE;
    }

    protected void end(){
    	Robot.shooter_pid.stop();
    }

    protected void interrupted(){
    	end();
    }
}
