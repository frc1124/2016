package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.drive.motion_prof.ContinualTargeting;
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
	private ContinualTargeting continual_aim;
	
	private BringShooterToSpeed shooter_cmd;
	
	private RampBeltsFeedToShooter feed_cmd;
	
	public static final double AUTO_SHOOTER_SPEED = 3655.0;
	
    public ScoreHighGoal() {
    	aim_cmd = new TrapezoidalAngleOutput();
    	short_aim = new LockOnToPixelTarget();
    	continual_aim = new ContinualTargeting();
    	
    	shooter_cmd = new BringShooterToSpeed();
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
       
        // ensure ball is in
        addParallel(new BallToSensor());
        
    	addSequential(aim_cmd);
        addSequential(short_aim);
        addParallel(continual_aim);
        
        addSequential(feed_cmd);
        
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
    	continual_aim = new ContinualTargeting();
    	
    	shooter_cmd = new BringShooterToSpeed(AUTO_SHOOTER_SPEED);
    	
    	feed_cmd = new RampBeltsFeedToShooter(shooter_cmd);

        addParallel(shooter_cmd);
       
        // ensure ball is in
        addParallel(new BallToSensor());
        
    	addSequential(aim_cmd);
        addSequential(short_aim);
        addParallel(continual_aim);
        
        addSequential(feed_cmd);
        addSequential(new CommandDelay(1.0));
        
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
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
