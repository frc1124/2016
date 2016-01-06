package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.commands.auto.AutoDrive;
import org.usfirst.frc.team1124.robot.commands.auto.TurnLeft;
import org.usfirst.frc.team1124.robot.commands.auto.TurnRight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class Autonomous extends CommandGroup {
    
    public  Autonomous() {
    	addSequential(new AutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnRight());
    	
    	addSequential(new AutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnLeft());

    	addSequential(new AutoDrive(0.5, 0.5, 1.5));
    	addSequential(new TurnLeft());

    	addSequential(new AutoDrive(0.5, 0.5, 1.5));
    }
}
