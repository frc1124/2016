package org.usfirst.frc.team1124.robot.commands.interrupt;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Trigger arm interrupt
 */
public class ArmInterrupt extends Command {

    public ArmInterrupt() {
        requires(Robot.arm_actuator);
        
        setInterruptible(false);
    }

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
