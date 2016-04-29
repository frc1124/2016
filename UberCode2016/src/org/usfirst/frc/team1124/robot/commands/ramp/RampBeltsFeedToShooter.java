package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.macro.ScoreHighGoal;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Run the ramp belts to feed the ball to the shooter to score a high goal.
 */
public class RampBeltsFeedToShooter extends Command {

	private BringShooterToSpeed shooter_cmd;
	private boolean auto = false;
	
    public RampBeltsFeedToShooter() {
    	requires(Robot.ramp_belts);
    	
    	setInterruptible(true);
    }
    
    public RampBeltsFeedToShooter(BringShooterToSpeed shooter_cmd) {
    	requires(Robot.ramp_belts);
    	
    	this.shooter_cmd = shooter_cmd;
    	
    	auto = shooter_cmd.getSetpoint() == ScoreHighGoal.AUTO_SHOOTER_SPEED;
    	
    	setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	if(shooter_cmd != null){
    		if(!shooter_cmd.isRunning()){
            	Robot.ramp_belts.feedToShooter();
    		}
    	}else{
        	Robot.ramp_belts.feedToShooter();
    	}
    }

    protected boolean isFinished() {
    	if(auto){
    		return !Robot.ramp_belts.getBallDetected();
    	}else{
        	return Robot.shooter_pid.getBallSensor();
    	}
    }

    protected void end() {
    	Robot.ramp_belts.stop();
    	
    	Robot.ramp_belts.removeBall();
    }

    protected void interrupted() {
    	Robot.ramp_belts.stop();
    }
}
