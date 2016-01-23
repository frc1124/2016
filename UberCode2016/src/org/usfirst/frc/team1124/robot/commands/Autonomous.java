package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.commands.drive.TimedAutoDrive;
import org.usfirst.frc.team1124.robot.commands.drive.TurnLeft;
import org.usfirst.frc.team1124.robot.commands.drive.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class Autonomous extends CommandGroup {
    
    public  Autonomous() {
    	addSequential(new TimedAutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnRight());
    	
    	addSequential(new TimedAutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnLeft());

    	addSequential(new TimedAutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnLeft());

    	addSequential(new TimedAutoDrive(0.5, 0.5, 1.5));
    }
}
