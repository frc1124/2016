package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Resting state for the shooter.
 */
public class ShooterRest extends Command {

    public ShooterRest() {
    	requires(Robot.shooter_pid);
    	
    	setInterruptible(true);
    }

    protected void initialize() {
    	Robot.shooter_pid.stop();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
