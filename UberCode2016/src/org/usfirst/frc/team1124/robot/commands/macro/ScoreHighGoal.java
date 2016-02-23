package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.camera.StreamShooterCamera;
import org.usfirst.frc.team1124.robot.commands.drive.AimTowardsGoal;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball.
 */
public class ScoreHighGoal extends CommandGroup {
	private StreamShooterCamera camera_command;
	
    public ScoreHighGoal() {
    	camera_command = new StreamShooterCamera();
    	
    	addParallel(camera_command);
    	
    	addSequential(new AimTowardsGoal());
        
        addSequential(new BringShooterToSpeed());
        addSequential(new RampBeltsFeedToShooter());
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1.5));
        addSequential(new ShooterInterrupt());
    }
    
    protected void end(){
    	super.end();
    	camera_command.cancel();
    }
    
    protected void interrupted(){
    	end();
    }
}
