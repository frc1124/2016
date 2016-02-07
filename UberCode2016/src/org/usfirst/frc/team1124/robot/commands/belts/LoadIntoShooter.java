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
 *	Runs belts to pass ball into the shooter wheel.
 */
public class LoadIntoShooter extends Command {

    public LoadIntoShooter()
    {
        requires(Robot.intake);
        requires(Robot.shooter_pid);
        
        setInterruptible(true);
    }

    protected void initialize()
    {
    	Robot.intake.intake();
    	setTimeout(2); // whatever duration required to get ball into the wheels
    }

    protected void execute() {}

    protected boolean isFinished()
    {
        return isTimedOut();
    }

    protected void end()
    {
    	Robot.intake.stop();
    	Robot.shooter_pid.disable();
    }

    protected void interrupted() {}
}
