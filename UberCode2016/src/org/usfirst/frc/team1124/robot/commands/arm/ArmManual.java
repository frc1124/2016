package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmManual extends Command {

    public ArmManual() {
        requires(Robot.arm_actuator_pid);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm_actuator_pid.disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm_actuator_pid.manual(Robot.oi.getJS2().getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
