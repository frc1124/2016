package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Run the ramp belts to feed the ball to the shooter to score a high goal. </br>
 * Runs for 1 second.
 */
public class RampBeltsFeedToShooter extends Command {

    public RampBeltsFeedToShooter() {
    	requires(Robot.ramp_belts);
    	
    	setInterruptible(true);
    	
    	// it should take about 1 second
    	/** TODO tune this */
    	setTimeout(1);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.ramp_belts.feedToShooter();
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.ramp_belts.stop();
    }

    protected void interrupted() {
    	end();
    }
}
