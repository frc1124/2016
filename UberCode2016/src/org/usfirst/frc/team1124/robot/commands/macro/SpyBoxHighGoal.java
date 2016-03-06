package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;
import org.usfirst.frc.team1124.robot.commands.shooter.HoldShooterSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Just shoot the ball :) </br>
 * ez spybot, ez life
 */
public class SpyBoxHighGoal extends CommandGroup {
    public SpyBoxHighGoal() {
    	// slower speed for 
        addSequential(new BringShooterToSpeed());
        addParallel(new HoldShooterSpeed());
        
        addSequential(new RampBeltsFeedToShooter());
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
    }
    
    protected void initialize(){
    	super.initialize();
    	
    	Robot.camera.setHeld(true);
    }
    
    protected void execute(){
    	super.execute();
    }
    
    protected void end(){
    	super.end();
    	
    	Robot.camera.setHeld(false);
    }
    
    protected void interrupted(){
    	super.interrupted();
    	
    	Robot.camera.setHeld(false);
    }
}
