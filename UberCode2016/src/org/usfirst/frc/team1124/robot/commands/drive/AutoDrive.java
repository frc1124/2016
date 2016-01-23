package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Use this to control autonomous driving, it manages both halves of the drive train.
 * This should be used both with autonomous mode driving and hold position/targeting code.
 * You should NOT directly use the LeftDrivePID or RightDrivePID classes since they need to be executed in PARALLEL.
 */
public class AutoDrive extends CommandGroup {
    public  AutoDrive(double leftSetpoint, double rightSetpoint) {
    	requires(Robot.drivetrain);
    	
    	addParallel(new LeftDrivePID(leftSetpoint));
    	addParallel(new RightDrivePID(rightSetpoint));
    }
}
