package org.usfirst.frc.team1124.robot.commands.ramp;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  Manually controls the ramp belts. 
 *  Uses Joystick 3, Y Axis.
 */
public class RampBeltsManual extends Command {

    public RampBeltsManual() {
    	requires(Robot.ramp_belts);
    	
    	setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	double speed = Robot.oi.getJS3().getY();
    	
    	Robot.ramp_belts.manual(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ramp_belts.stop();
    }
    
    protected void interrupted() {
    	end();
    }
}
