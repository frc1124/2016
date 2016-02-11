package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Use voltage instead of a PID to control the shooter. </br>
 * ONLY USE IF THE SHOOTER ENCODER IS BROKEN/DISCONNECTED!!!!!
 */
public class BringShooterToApproxSpeed extends Command {
	
	private double voltage = 0.00;

    public BringShooterToApproxSpeed() {
    	requires(Robot.shooter_pid);
    	
    	// TODO set this to an actual value
    	voltage = 0;
    	
    	setInterruptible(true);
    }

    protected void initialize() {
    	Robot.shooter_pid.stop();
    }

    protected void execute() {
    	Robot.shooter_pid.manual(voltage);
    }

    protected boolean isFinished() {
    	// TODO make this stop eventually pls
        return false;
    }

    protected void end() {
    	Robot.shooter_pid.manual(0);
    	Robot.shooter_pid.resume();
    }

    protected void interrupted() {
    	end();
    }
}
