package org.usfirst.frc.team1124.robot.commands.interrupt;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Trigger shooter interrupt
 */
public class ShooterInterrupt extends Command {

    public ShooterInterrupt() {
        requires(Robot.shooter_pid);
        
        setInterruptible(false);
    }

    protected void initialize() {
    	Robot.shooter_pid.setSetpoint(0.0);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	Robot.shooter_pid.stop();
    }

    protected void interrupted() {}
}
