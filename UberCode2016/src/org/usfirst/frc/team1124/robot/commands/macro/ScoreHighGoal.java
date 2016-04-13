package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.drive.motion_prof.FreezeDrive;
import org.usfirst.frc.team1124.robot.commands.drive.motion_prof.LockOnToPixelTarget;
import org.usfirst.frc.team1124.robot.commands.drive.motion_prof.TrapezoidalAngleOutput;
import org.usfirst.frc.team1124.robot.commands.interrupt.DriveTrainInterrupt;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.BallToSensor;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball :)
 */
public class ScoreHighGoal extends CommandGroup {
	private TrapezoidalAngleOutput aim_cmd;
	private LockOnToPixelTarget short_aim;
	private FreezeDrive lock_cmd;
	
	private BringShooterToSpeed shooter_cmd;
	
	private RampBeltsFeedToShooter feed_cmd;
	
    public ScoreHighGoal() {
    	aim_cmd = new TrapezoidalAngleOutput();
    	short_aim = new LockOnToPixelTarget();
    	lock_cmd = new FreezeDrive();
    	
    	shooter_cmd = new BringShooterToSpeed();
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
       
        // ensure ball is in
        addParallel(new BallToSensor());
        
    	addSequential(aim_cmd);
        addSequential(short_aim);
        addParallel(lock_cmd);
        
        addSequential(feed_cmd);
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
    }
	
    /**
     * Run a faster shot
     * @param auto doesn't mater what this is, just have a byte to select this method
     */
    public ScoreHighGoal(byte auto) {
    	aim_cmd = new TrapezoidalAngleOutput();
    	short_aim = new LockOnToPixelTarget();
    	lock_cmd = new FreezeDrive();
    	
    	shooter_cmd = new BringShooterToSpeed(3625.0);
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
       
        // ensure ball is in
        addParallel(new BallToSensor());
        
    	addSequential(aim_cmd);
        addSequential(short_aim);
        addParallel(lock_cmd);
        
        addSequential(feed_cmd);
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
    }
    
    protected void initialize(){
    	super.initialize();

    	Robot.camera.setHeld(true);
    }
    
    protected void end(){
    	super.end();
    	
    	Robot.ramp_belts.removeBall();
    	
    	Robot.camera.setHeld(false);
    }
    
    protected void interrupted(){
    	super.interrupted();
    	
    	end();
    }
}
