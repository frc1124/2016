package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.camera.StreamShooterCamera;
import org.usfirst.frc.team1124.robot.commands.drive.AimTowardsGoalPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball.
 */
public class ScoreHighGoal extends CommandGroup {
	StreamShooterCamera camera_command;
	
    public ScoreHighGoal() {
    	camera_command = new StreamShooterCamera();
    	
    	addParallel(camera_command);
    	
    	addSequential(new AimTowardsGoalPID());
    	
        addSequential(new Shoot());
    }
    
    protected void end(){
    	camera_command.cancel();
    }
    
    protected void interrupted(){
    	end();
    }
}
