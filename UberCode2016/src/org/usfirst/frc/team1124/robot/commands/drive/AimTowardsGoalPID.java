package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class AimTowardsGoalPID extends PIDCommand {
	private static final double P = 0.00001;
	private static final double I = 0;
	private static final double D = 0;
	
	private static final int cam_width = 320;
	private static boolean isDone = false;
	
    // Initialize your subsystem here
    public AimTowardsGoalPID() {
    	super("AimTowardsGoalPID", P, I, D);
    	setInterruptible(true);
    }

	@Override
	protected double returnPIDInput() {
		double center = Robot.db_connection.getTargetCenterOfMass()[0];
		if(center == -1){
			isDone = true;
			return this.getSetpoint();
		}
		else return Robot.db_connection.getTargetCenterOfMass()[0];
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive_tank_auto(output, (-1) * output);
	}

	@Override
	protected void end() {}

	@Override
	protected void execute() {}

	@Override
	protected void initialize() {
		setSetpoint(cam_width / 2);
	}

	@Override
	protected void interrupted() {}

	@Override
	protected boolean isFinished() {
		return isDone;
	}
}
