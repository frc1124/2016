package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Run the ramp belts to feed the ball to the shooter to score a high goal. </br>
 * Runs for 1 second.
 */
public class RampBeltsFeedToShooter extends Command {

    public RampBeltsFeedToShooter() {
    	requires(Robot.ramp_belts_pid);
    	
    	setInterruptible(true);
    	
    	// it should take about 1 second
    	/** TODO tune this */
    	setTimeout(1);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.feedToShooter();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Scheduler.getInstance().add(new RampHoldPosition());	//why hold position at end? Do we gain anything from this?
    }

    protected void interrupted() {
    	end();
    }
}
