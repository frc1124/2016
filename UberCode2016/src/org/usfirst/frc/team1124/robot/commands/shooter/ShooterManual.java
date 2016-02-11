package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manually control shooter (why are you doing this)
 * Uses Joystick 2, Y Axis
 */
public class ShooterManual extends Command {

    public ShooterManual() {
        requires(Robot.shooter_pid);
    }

    protected void initialize() {
    	Robot.shooter_pid.stop();
    }

    protected void execute() {
    	double speed = Robot.oi.getJS2().getY();
    	
    	Robot.shooter_pid.manual(speed);
    }

    protected boolean isFinished() {
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
