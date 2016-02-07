package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Hold the ramp belts at a rate of 0 until interrupted
 */
public class RampHoldPosition extends Command {

    public RampHoldPosition() {
        requires(Robot.ramp_belts_pid);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.holdPosition();
    }

    protected void execute() {}

    protected boolean isFinished() {
    	// never finishes, just is interrupted
        return false;
    }

    protected void end() {
    	Robot.ramp_belts_pid.stop();
    }

    protected void interrupted() {
    	end();
    }
}
