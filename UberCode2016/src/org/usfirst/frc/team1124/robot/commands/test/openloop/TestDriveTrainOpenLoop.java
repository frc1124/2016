package org.usfirst.frc.team1124.robot.commands.test.openloop;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestDriveTrainOpenLoop extends CommandGroup {
	private FindStableP left = null;	
	private FindStableP right = null;	

	@Override
	protected void initialize() {
		requires(Robot.drivetrain);
		this.left = new FindStableP(TryPForLeft.class);
		this.right = new FindStableP(TryPForRight.class);
		this.addSequential(this.left);
		this.addSequential(this.right);
	}

	@Override
	protected boolean isFinished() {
		return (this.left.isRunning() && this.right.isRunning());
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();

		// Send left and right PID to SmartDashboard
		SmartDashboard.putNumber("left_p",this.left.getP());
		SmartDashboard.putNumber("left_i",this.left.getI());
		SmartDashboard.putNumber("left_d",this.left.getD());
		SmartDashboard.putNumber("right_p",this.right.getP());
		SmartDashboard.putNumber("right_i",this.right.getI());
		SmartDashboard.putNumber("right_d",this.right.getD());
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}
}