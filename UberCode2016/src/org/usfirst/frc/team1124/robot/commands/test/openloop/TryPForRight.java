package org.usfirst.frc.team1124.robot.commands.test.openloop;

import org.usfirst.frc.team1124.robot.Robot;

public class TryPForRight extends TryP {

	public TryPForRight(double offset, double p) {
		super(p);
		this.setpoint = Robot.drivetrain.getRightEncoderDistance() + offset;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.drivetrain.getRightEncoderDistance();
	}

	@Override
	protected boolean isMoving() {
		return (Robot.drivetrain.getRightEncoderRate() != 0.0);
	}
}
