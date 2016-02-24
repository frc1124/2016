package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aim towards the goal using our own control system <u>instead</u> of a PID.
 */
public class AimTowardsGoal extends Command {
	private static final double setpoint = 160;
	private static final double TOLERANCE = 8.5;
	
	private boolean onTarget = false;
	
	private double abs_error = 0;
	private double x = 0;

    public AimTowardsGoal() {
        requires(Robot.drivetrain);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	// so we don't drift
    	Robot.drivetrain.setBrake();
    }

    protected void execute() {
    	//x = Robot.camera.getTargetCenterOfMass()[0];
		x = SmartDashboard.getNumber("vision_target_x_cm");
    	abs_error = Math.abs(setpoint - x);
    	
    	System.out.println(abs_error + " x: " + x);
    	
    	double output = 0.0825094541 + 0.0516823406 * Math.log(abs_error);
    	
    	if(x > setpoint + TOLERANCE){
    		/*
    		if(abs_error < 20){
        		Robot.drivetrain.drive_tank_auto(0.2, -0.2);
    		}else if(abs_error < 3){
        		Robot.drivetrain.drive_tank_auto(0.15, -0.15);
    		}else{
        		Robot.drivetrain.drive_tank_auto(0.24, -0.24);
    		}
    	}else if(x < setpoint - TOLERANCE){
    		if(abs_error < 20){
        		Robot.drivetrain.drive_tank_auto(-0.2, 0.2);
    		}else if(abs_error < 3){
        		Robot.drivetrain.drive_tank_auto(-0.15, 0.15);
    		}else{
        		Robot.drivetrain.drive_tank_auto(-0.24, 0.24);
    		}
    		*/
        	Robot.drivetrain.drive_tank_auto(output, -output);
    	}else if(x < setpoint - TOLERANCE){
    		Robot.drivetrain.drive_tank_auto(-output, output);
    	}else{
    		onTarget = true;
    	}
    }

    protected boolean isFinished() {
        return onTarget();
    }
    
    public boolean onTarget(){
    	return onTarget;
    }

    protected void end() {
    	Robot.drivetrain.stop();
    	
    	onTarget = false;
    	x = 0;
    	
    	//DriveHoldPosition hold = new DriveHoldPosition();
    	//hold.start();
    	
    	Robot.drivetrain.setCoast();
    }

    protected void interrupted() {
    	Robot.drivetrain.stop();
    	
    	onTarget = false;
    	x = 0;
    	
    	Robot.drivetrain.setCoast();
    }
}
