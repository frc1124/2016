package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.drive.targeting.AimAtAnglePID;
import org.usfirst.frc.team1124.robot.commands.drive.targeting.HoldAtVoltage;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;
import org.usfirst.frc.team1124.robot.commands.shooter.HoldShooterSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball :)
 */
public class ScoreHighGoal extends CommandGroup {
	private AimAtAnglePID aim_cmd;
	private HoldAtVoltage hold_cmd;
	
    public ScoreHighGoal() {
    	aim_cmd = new AimAtAnglePID();
    	hold_cmd = new HoldAtVoltage(aim_cmd);
    	
    	addSequential(aim_cmd);
        addParallel(hold_cmd);
    	
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
    
    protected void end(){
    	super.end();
    	
    	Robot.camera.setHeld(false);
    }
    
    protected void interrupted(){
    	super.interrupted();
    	
    	end();
    }
}
