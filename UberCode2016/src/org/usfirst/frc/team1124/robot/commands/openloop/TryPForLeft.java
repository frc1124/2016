package org.usfirst.frc.team1124.robot.commands.openloop;

import org.usfirst.frc.team1124.robot.Robot;

public class TryPForLeft extends TryP {

	public TryPForLeft(double offset, double p) {
		super(p);
		this.setpoint = Robot.drivetrain.getLeftEncoderDistance() + offset;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.drivetrain.getLeftEncoderDistance();
	}

	@Override
	protected boolean isMoving() {
		return (Robot.drivetrain.getLeftEncoderRate() != 0.0);
	}
}
