package org.usfirst.frc.team1124.robot.commands.intake;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manually controls the intake wheels on the arm with joystick input
 * Uses Joystick 2, Y Axis
 */
public class IntakeManual extends Command {

    public IntakeManual() {
        requires(Robot.arm_intake_wheels);
        
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	double speed = Robot.oi.getJS2().getY();
    	
    	Robot.arm_intake_wheels.manual(-speed);
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
