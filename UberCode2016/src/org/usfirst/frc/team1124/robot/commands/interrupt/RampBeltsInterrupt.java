package org.usfirst.frc.team1124.robot.commands.interrupt;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Trigger ramp belts interrupt
 */
public class RampBeltsInterrupt extends Command {

    public RampBeltsInterrupt() {
    	requires(Robot.ramp_belts);
    	
    	setInterruptible(false);
    }
    
    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
