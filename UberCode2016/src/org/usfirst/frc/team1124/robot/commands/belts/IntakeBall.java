package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//_____  _____  ______   ________  _______      ______        _       _____     _____      ______   
//|_   _||_   _||_   _ \ |_   __  ||_   __ \    |_   _ \      / \     |_   _|   |_   _|   .' ____ \  
//  | |    | |    | |_) |  | |_ \_|  | |__) |     | |_) |    / _ \      | |       | |     | (___ \_| 
//  | '    ' |    |  __'.  |  _| _   |  __ /      |  __'.   / ___ \     | |   _   | |   _  _.____`.  
//   \ \__/ /    _| |__) |_| |__/ | _| |  \ \_   _| |__) |_/ /   \ \_  _| |__/ | _| |__/ || \____) | 
//    `.__.'    |_______/|________||____| |___| |_______/|____| |____||________||________| \______.' 
//                                                                                                  
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

    protected void interrupted() {}
}
