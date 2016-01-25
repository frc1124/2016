package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmPistonsRetract extends Command {

    public ArmPistonsRetract() {
        requires(Robot.arm_pistons);
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_pistons.retract();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
