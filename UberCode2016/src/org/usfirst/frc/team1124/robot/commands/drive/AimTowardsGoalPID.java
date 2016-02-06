package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class AimTowardsGoalPID extends PIDCommand {
	private static final double P = 0.0013999;
	private static final double I = 0.0002578;
	private static final double D = 0.0048184;
	
	private static final int cam_width = 320;
	private static boolean isDone = false;
	
    public AimTowardsGoalPID() {
    	super("AimTowardsGoalPID", P, I, D);
    	
    	requires(Robot.drivetrain);
    	setInterruptible(true);
    }

	protected double returnPIDInput() {
		double center = Robot.db_connection.getTargetCenterOfMass()[0];
		
		return center;
	}

	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive_tank_auto((-1) * output, output);
	}

	protected void end() {}

	protected void execute() {
		double center = Robot.db_connection.getTargetCenterOfMass()[0];
		
		if(center == 0){
			getPIDController().disable();
			getPIDController().reset();
		}else{
			getPIDController().enable();
		}
	}
	
	protected void initialize() {
		setSetpoint(cam_width / 2);
	}

	protected void interrupted() {}

	protected boolean isFinished() {
		return isDone;
	}
}
