package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Starts a hold position loop that also incorporates feed-forward control from the driver
 * TODO test this code
 */
public class DriveHoldPosition extends CommandGroup {
	
	private LeftDrivePID left_drive;
	private RightDrivePID right_drive;
	
	private double left_setpoint = 0;
	private double right_setpoint = 0;
	
	private boolean wasActive = false;
	private Timer timer;
	
	private double left_speed = 0;
	private double right_speed = 0;
	
	private double left_dist = 0;
	private double right_dist = 0;
	
	
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
    protected void initialize() {
		timer = new Timer();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double y = Robot.oi.getController().getY() * -1;
    	double x = Robot.oi.getController().getX();
    	
    	if(Math.abs(y) > threshold || Math.abs(x) > threshold){
    		if(!wasActive){
    			Robot.drivetrain.resetEncoders();
    			
    			timer.reset();
    			timer.start();
    			
    			left_dist = 0;
    			right_dist = 0;
    		}
    		
    		wasActive = true;
    		
    		if(this.arcade){
    			double[] vals = arcade(x, y);
    			
    			left_speed = vals[0];
    			right_speed = vals[1];
    		}else{
    			left_speed = y;
    			right_speed = x;
    		}
    		
    		left_dist += (left_speed / 2) * timer.get() * ((2.0/3.0) * 100);
    		right_dist += (right_speed / 2) * timer.get() * ((2.0/3.0) * 100);
    		
    		left_drive.updateSetpoint(left_dist);
    		right_drive.updateSetpoint(right_dist);
    	}else{
    		if(wasActive){
    			Robot.drivetrain.resetEncoders();
    		}
    		
    		wasActive = false;
    		
    		left_drive.updateSetpoint(0);
    		right_drive.updateSetpoint(0);
    	}
    	
    	
    }

    /** 
     * @param x = x axis
     * @param y = y axis
     * 
     * @return double array {left_speed, right_speed}*/
    private double[] arcade(double x, double y){
    	double leftMotorSpeed = 0.00;
    	double rightMotorSpeed = 0.00;
    	
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
		
		double[] speeds = {leftMotorSpeed, rightMotorSpeed};
		
		return speeds;
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
