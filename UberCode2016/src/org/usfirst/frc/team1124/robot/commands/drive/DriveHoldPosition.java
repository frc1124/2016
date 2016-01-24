package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts a hold position loop that also incorporates feed-forward control from the driver
 */
public class DriveHoldPosition extends CommandGroup {
	
	private LeftDrivePID left_drive;
	private RightDrivePID right_drive;
	
	private double left_setpoint = 0;
	private double right_setpoint = 0;

	private boolean arcade = true;
	private double threshold = 0.4;
	
	/** New hold position command, arcade drive = true, threshold = 0.4*/
    public DriveHoldPosition() {
    	requires(Robot.drivetrain);
    	
    	setInterruptible(true);
    	
    	left_drive = new LeftDrivePID(left_setpoint);
    	right_drive = new RightDrivePID(right_setpoint);
    	
    	addParallel(left_drive);
    	addParallel(right_drive);
    }
	
    /** New hold position command, threshold = 0.4*/
    public DriveHoldPosition(boolean arcade) {
    	requires(Robot.drivetrain);
    	
    	setInterruptible(true);
    	
    	left_drive = new LeftDrivePID(left_setpoint);
    	right_drive = new RightDrivePID(right_setpoint);
    	
    	addParallel(left_drive);
    	addParallel(right_drive);
    	
    	this.arcade = arcade;
    	this.threshold = arcade ? 0.4 : 0.05;
    }
    
    /** New hold position command*/
    public DriveHoldPosition(boolean arcade, double threshold) {
    	requires(Robot.drivetrain);
    	
    	setInterruptible(true);
    	
    	left_drive = new LeftDrivePID(left_setpoint);
    	right_drive = new RightDrivePID(right_setpoint);
    	
    	addParallel(left_drive);
    	addParallel(right_drive);
    	
    	this.arcade = arcade;
    	this.threshold = threshold;
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double y = Robot.oi.getController().getY() * -1;
    	double x = Robot.oi.getController().getX();
    	
    	double leftMotorSpeed = 0.00;
    	double rightMotorSpeed = 0.00;
    	
    	if(arcade){
	    	// arcade drive math
			if(y >= 0.0){
				y = (y * y);
			}else{
				y = -(y * y);
			}
			
			if(x >= 0.0){
				x = (x * x);
			}else{
				x = -(x * x);
			}
			
			if(y > 0.0){
				if(x > 0.0){
					leftMotorSpeed = y - x;
					rightMotorSpeed = Math.max(y, x);
				}else{
					leftMotorSpeed = Math.max(y, -x);
					rightMotorSpeed = y + x;
				}
			}else{
				if(x > 0.0){
					leftMotorSpeed = -Math.max(-y, x);
					rightMotorSpeed = y + x;
				}else{
					leftMotorSpeed = y - x;
					rightMotorSpeed = -Math.max(-y, -x);
				}
			}
    	}else{
    		leftMotorSpeed = x;
    		rightMotorSpeed = y;
    	}
		
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	Robot.drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }
}
