package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.commands.drive.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PIDTuner extends CommandGroup {
    
    public  PIDTuner() {
        addSequential(new AutoDrive(50, 50));
    }
}
