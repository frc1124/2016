package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunRampBelts extends Command {
	
	private final double MOVE_BALL_SPEED = 0;

    public RunRampBelts() {
    	requires(Robot.ramp_belts_pid);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.setSetpoint(MOVE_BALL_SPEED);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
