package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class UpdatePositionTracking extends Command {

	public UpdatePositionTracking() {
		requires(Robot.drivetrain);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.positionTracker.updateFromEncoders(Robot.drivetrain.getLeftEncoderDistance(),Robot.drivetrain.getRightEncoderDistance());
	}

	@Override
	protected boolean isFinished() {
		return false; // Do this forever
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
