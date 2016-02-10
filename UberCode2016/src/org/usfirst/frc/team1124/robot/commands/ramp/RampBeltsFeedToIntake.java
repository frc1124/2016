package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Feed the ball back to the intake to score a low goal. </br>
 * Runs for 1 second.
 */
public class RampBeltsFeedToIntake extends Command {

    public RampBeltsFeedToIntake() {
    	requires(Robot.ramp_belts_pid);
    	
    	setInterruptible(true);
    	
    	// it should take about 1 second
    	/** TODO tune this */
    	setTimeout(1);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.feedToShooter();	//should this not be Robot.ramp_belts_pid.feedToIntake(); ?
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Scheduler.getInstance().add(new RampHoldPosition());	//see comment in RampBeltsFeedToShooter.java
    }

    protected void interrupted() {
    	end();
    }
}
