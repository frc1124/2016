package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Use this to control autonomous driving, it manages both halves of the drive train.
 * This should be used both with autonomous mode driving and hold position/targeting code.
 * You should NOT directly use the LeftDrivePID or RightDrivePID classes since they need to be executed in PARALLEL.
 */
public class AutoDrive extends CommandGroup {
	private LeftDrivePID left;
	private RightDrivePID right;
	
	public  AutoDrive(double leftSetpoint, double rightSetpoint) {
    	requires(Robot.drivetrain);
    	
    	left = new LeftDrivePID(leftSetpoint);
    	right = new RightDrivePID(rightSetpoint);
    	
    	addParallel(left);
    	addParallel(right);

    	/** TODO remove this when done tuning */
    	SmartDashboard.putString("auto-state", "running");
    }
    
    protected void execute(){
    	double left_speed = left.getSpeed();		//So this gets the speed setpoints of the right and left
    	double right_speed = right.getSpeed();		//drive PIDs, and then sets the auto tank drive to drive
    												//at those speeds? I feel like I'm missing something.
    	Robot.drivetrain.drive_tank_auto(left_speed, right_speed);
    	
    	if(left.isSideFinished() && right.isSideFinished()){
    		/** TODO re-enable this when we are done tuning */
    		//left.stop();
    		//right.stop();
    	}

    	/** TODO remove this when done tuning */
    	if(left.isFinished()){
    		SmartDashboard.putString("LEFT_STATE", "FINISHED");
    	}else{
    		SmartDashboard.putString("LEFT_STATE", "RUNNING");
    	}
    	
    	if(right.isFinished()){
    		SmartDashboard.putString("RIGHT_STATE", "FINISHED");
    	}else{
    		SmartDashboard.putString("RIGHT_STATE", "RUNNING");
    	}
    }
    
    protected boolean isFinished(){
    	super.isFinished();
    	
    	return left.isFinished() && right.isFinished();
    }
    
    protected void end(){
    	/** TODO remove dashboard stuff when done tuning */
    	SmartDashboard.putString("auto-state", "done");
    	
    	Robot.drivetrain.stop();
    }
    
    protected void interrupted(){
    	end();
    }
}
