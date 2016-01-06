package org.usfirst.frc.team1124.robot.commands.auto;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {
	
	private double speed_left;
	private double speed_right;
	
    public AutoDrive(double speed_left, double speed_right, double duration) {
        requires(Robot.drivetrain);
        
        this.speed_left = speed_left;
        this.speed_right = speed_right;
        
        setTimeout(duration);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// reset encoders
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.drive_tank(speed_left, speed_right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
