package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Ball intake. Belts will run at intake speed set in IntakeBelts subsystem, then stop when light sensor detects ball.
 */
public class IntakeBall extends Command {

    public IntakeBall()
    {
        requires(Robot.intake_belts);
        
        setInterruptible(true);
    }

    protected void initialize()
    {
    	Robot.intake_belts.intake();
    }

    protected void execute()
    {
    	if (Robot.intake_belts.getSensorState())
    	{
    		end();
    	}
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	Robot.intake_belts.stop();
    }

    protected void interrupted()
    {
    	
    }
}
