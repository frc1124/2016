package org.usfirst.frc.team1124.robot.commands.interrupt;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Trigger intake interrupt
 */
public class IntakeInterrupt extends Command {

    public IntakeInterrupt() {
        requires(Robot.arm_intake_wheels);
        
        setInterruptible(false);
    }

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	Robot.shooter_pid.stop();
    }

    protected void interrupted() {}
}
