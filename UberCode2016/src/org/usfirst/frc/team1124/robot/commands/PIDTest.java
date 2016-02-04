package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.commands.drive.AutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PIDTest extends CommandGroup {
    
    public PIDTest() {
        addSequential(new AutoDrive(18, -18, true));
    }
}
