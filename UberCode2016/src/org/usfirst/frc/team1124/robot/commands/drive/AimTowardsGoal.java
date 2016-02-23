package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimTowardsGoal extends Command {
	private static final int setpoint = 160;
	
	private boolean isDone = false;

    public AimTowardsGoal() {
        requires(Robot.drivetrain);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	Robot.drivetrain.setBrake();
    }

    protected void execute() {
    	double x = Robot.camera.getTargetCenterOfMass()[0];
    	
    	
    	if(x > setpoint - 1){
    		Robot.drivetrain.drive_tank_auto(0.15, -0.15);
    	}else if(x < setpoint + 1){
    		Robot.drivetrain.drive_tank_auto(-0.15, 0.15);
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
