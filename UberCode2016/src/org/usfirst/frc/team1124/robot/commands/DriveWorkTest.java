package org.usfirst.frc.team1124.robot.commands;

import org.usfirst.frc.team1124.robot.commands.drive.TimedAutoDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * For testing the CIMs by just running them.
 */
public class DriveWorkTest extends CommandGroup {
    
    public  DriveWorkTest() {
        addSequential(new TimedAutoDrive(0.5, 0.5, 30.0 * 60.0));
    }
}
