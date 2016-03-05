package org.usfirst.frc.team1124.robot.commands.intake;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Intake in a ball
 */
public class IntakeIn extends Command {

    public IntakeIn() {
        requires(Robot.arm_intake_wheels);
        
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_intake_wheels.intake();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.arm_intake_wheels.stop();
    }

    protected void interrupted() {
    	end();
    }
}
