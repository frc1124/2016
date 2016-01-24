package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowGoal extends Command {

    public LowGoal()
    {
    	requires(Robot.intake_belts);
    	
    	setInterruptible(true);
    	
    	setTimeout(2); // they said two seconds would be long enough so i'll take their word for it
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    	Robot.intake_belts.spit();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end()
    {
    	Robot.intake_belts.stop();
    }

    protected void interrupted() {}
}
