package org.usfirst.frc.team1124.robot.commands.intake;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A timed command that spits the ball out of the robot into the low goal.
 */
public class LowGoal extends Command {

    public LowGoal(){
    	requires(Robot.arm_intake_wheels);
    	
    	setInterruptible(true);
    	
    	// approximatly 2 seconds
    	setTimeout(2);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_intake_wheels.spit();
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end(){
    	Robot.arm_intake_wheels.stop();
    }

    protected void interrupted() {
    	end();
    }
}
