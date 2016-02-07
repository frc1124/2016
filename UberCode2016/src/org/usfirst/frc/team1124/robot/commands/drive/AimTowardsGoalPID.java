package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Use PID and pixel data to turn towards the target
 */
public class AimTowardsGoalPID extends PIDCommand {
	/** TODO finish tuning these */
	private static final double P = 0.0023666;
	private static final double I = 0.00021213;
	private static final double D = 0.002804366760;
	
	private static final int image_width = 320;
	private static boolean is_done = false;
	
    public AimTowardsGoalPID() {
    	super("AimTowardsGoalPID", P, I, D);
    	
    	requires(Robot.drivetrain);
    	setInterruptible(true);
    }
    
	protected double returnPIDInput() {
		return Robot.camera_system.getTargetCenterOfMass()[0];
	}
	
	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive_tank_auto((-1) * output, output);
	}
	
	protected void execute() {
		double center = Robot.camera_system.getTargetCenterOfMass()[0];

		/** TODO change this later? */
		if(center == 0){
			getPIDController().disable();
			getPIDController().reset();
		}else{
			getPIDController().enable();
		}
	}
	
	protected void initialize() {
		setSetpoint(image_width / 2);
	}

	protected boolean isFinished() {
		return is_done;
	}
	
	protected void end() {}

	protected void interrupted() {}
}
