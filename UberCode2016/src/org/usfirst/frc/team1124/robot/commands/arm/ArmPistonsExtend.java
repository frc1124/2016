package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmPistonsExtend extends Command {

    public ArmPistonsExtend() {
    	requires(Robot.arm_pistons);
    	setInterruptible(true);
    }

    protected void initialize() {
    	Robot.arm_pistons.extend();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
