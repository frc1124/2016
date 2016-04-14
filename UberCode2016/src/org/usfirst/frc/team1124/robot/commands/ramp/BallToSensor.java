package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Pull the ball back in in case it fell
 */
public class BallToSensor extends Command {

    public BallToSensor() {
        requires(Robot.ramp_belts);
        
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.ramp_belts.intake();
    }

    protected boolean isFinished() {
        return Robot.ramp_belts.getBallDetected();
    }

    protected void end() {
    	Robot.ramp_belts.stop();
    }
    
    protected void interrupted() {
    	end();
    }
}
