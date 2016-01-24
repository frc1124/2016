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
    	addSequential(new TimedAutoDrive(0.75, 0.75, 10));
    }
}
