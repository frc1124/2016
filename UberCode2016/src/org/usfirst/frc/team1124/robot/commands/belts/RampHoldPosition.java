package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RampHoldPosition extends Command {

    public RampHoldPosition() {
        requires(Robot.ramp_belts_pid);
    }

    protected void initialize() {
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
