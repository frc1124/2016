package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopRampBelts extends Command {

    public StopRampBelts() {
        requires(Robot.ramp_belts_pid);
    }

    protected void initialize() {
    	Robot.ramp_belts_pid.setSetpoint(0);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {
    }
}
