package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Hold the ramp belts at a rate of 0
 */
public class RampHoldPosition extends Command {

    public RampHoldPosition() {
        requires(Robot.ramp_belts_pid);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.setSetpoint(0);
    	Robot.ramp_belts_pid.enable();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ramp_belts_pid.disable();
    }

    protected void interrupted() {
    	end();
    }
}
