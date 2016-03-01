package org.usfirst.frc.team1124.robot.commands.drive.targeting;

import org.usfirst.frc.team1124.robot.commands.drive.LeftDrivePID;
import org.usfirst.frc.team1124.robot.commands.drive.RightDrivePID;
import org.usfirst.frc.team1124.robot.tools.VisionTools;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aim at an angle using a conversion of the angle to rotational inches
 */
public class AimUsingDriveEncoders extends CommandGroup {	
	private double angle = 0;
	
	private double CONVERSION_FACTOR = 0;
	
	private LeftDrivePID left_drive;
	private RightDrivePID right_drive;
	
    public AimUsingDriveEncoders() {
    	double setpoint = 0;
    	
    	try{
	    	double xlhsGoalBBox = SmartDashboard.getNumber("vision_target_left");
	    	double widthGoalBBox = SmartDashboard.getNumber("vision_target_width");
	    	
	    	System.out.println("Target Left X: " + xlhsGoalBBox + " Width: " + widthGoalBBox);
	    	
	    	setpoint = VisionTools.turnAngle(xlhsGoalBBox, widthGoalBBox, true);
	    	
	    	System.out.println("Setpoint: " + setpoint);
    	}catch(Exception oh_no){
    		System.out.println("Fatal Targeting Error: Dashboard data not found.");
    	}
        
    	angle = setpoint * CONVERSION_FACTOR;
    	
    	left_drive = new LeftDrivePID(setpoint);
    	right_drive = new RightDrivePID(-setpoint);
    	
    	left_drive.setToRotatePID();
    	right_drive.setToRotatePID();
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
