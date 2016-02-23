package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Aim towards the goal using our own control system <u>instead</u> of a PID.
 */
public class AimTowardsGoal extends Command {
	private static final double setpoint = 160;
	private static final double TOLERANCE = 1.5;
	
	private boolean isDone = false;
	
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
    	x = Robot.camera.getTargetCenterOfMass()[0];
    	abs_error = Math.abs(setpoint - x); 
    	
    	if(x > setpoint + TOLERANCE){
    		if(abs_error < 80){
        		Robot.drivetrain.drive_tank_auto(0.15, -0.15);
    		}else if(abs_error < 10){
        		Robot.drivetrain.drive_tank_auto(0.1, -0.1);
    		}else{
        		Robot.drivetrain.drive_tank_auto(0.2, -0.2);
    		}
    	}else if(x < setpoint - TOLERANCE){
    		if(abs_error < 80){
        		Robot.drivetrain.drive_tank_auto(-0.15, 0.15);
    		}else if(abs_error < 10){
        		Robot.drivetrain.drive_tank_auto(-0.1, 0.1);
    		}else{
        		Robot.drivetrain.drive_tank_auto(-0.2, 0.2);
    		}
    	}else{
    		Robot.drivetrain.stop();
    		
    		isDone = true;
    	}
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.drivetrain.stop();
    	
    	Robot.drivetrain.setCoast();
    }

    protected void interrupted() {
    	end();
    }
}
