package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.drive.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class Autonomous extends CommandGroup {
    
    public  Autonomous() {
    	addSequential(new AutoDrive(60, 60));
    }
}
