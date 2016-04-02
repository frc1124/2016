package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.drive.ArcadeDriveJoystick;
import org.usfirst.frc.team1124.robot.commands.drive.targeting.AimAtAnglePID;
import org.usfirst.frc.team1124.robot.commands.drive.targeting.HoldAtVoltage;
import org.usfirst.frc.team1124.robot.commands.interrupt.DriveTrainInterrupt;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;
import org.usfirst.frc.team1124.robot.commands.shooter.HoldShooterAtPrimingSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball :)
 */
public class ScoreHighGoal extends CommandGroup {
	private AimAtAnglePID aim_cmd;
	private HoldAtVoltage hold_cmd;
	
	private BringShooterToSpeed shooter_cmd;
	
	private RampBeltsFeedToShooter feed_cmd;
	
	private HoldShooterAtPrimingSpeed prime_cmd;
	
    public ScoreHighGoal() {
    	aim_cmd = new AimAtAnglePID();
    	hold_cmd = new HoldAtVoltage(aim_cmd);
    	
    	shooter_cmd = new BringShooterToSpeed();
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
        
    	addSequential(aim_cmd);
        addParallel(hold_cmd);
        
        addSequential(feed_cmd);
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
        
        prime_cmd = new HoldShooterAtPrimingSpeed();
    }
	
    /**
     * Run a faster shot
     * @param auto doesn't mater what this is, just have a byte to select this method
     */
    public ScoreHighGoal(byte auto) {
    	aim_cmd = new AimAtAnglePID();
    	hold_cmd = new HoldAtVoltage(aim_cmd);
    	
    	shooter_cmd = new BringShooterToSpeed(3625.0);
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
        
    	addSequential(aim_cmd);
        addParallel(hold_cmd);
        
        addSequential(feed_cmd);
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
        
        prime_cmd = new HoldShooterAtPrimingSpeed();
    }
    
    protected void initialize(){
    	super.initialize();

    	Robot.camera.setHeld(true);
    }
    
    protected void end(){
    	super.end();
    	
    	Robot.camera.setHeld(false);
    	
    	prime_cmd.start();
    }
    
    protected void interrupted(){
    	super.interrupted();
    	
    	end();
    }
}
