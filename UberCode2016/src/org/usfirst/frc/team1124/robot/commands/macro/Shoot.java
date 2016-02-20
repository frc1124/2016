package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToShooter;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Shoot the ball (run this after we are aimed and ready). This is run by ScoreHighGoal.java
 */
public class Shoot extends CommandGroup {
    
    public Shoot() {
        addParallel(new BringShooterToSpeed());
        
        addSequential(new RampBeltsFeedToShooter());
        
        // wait to be sure it fired and is done
        addSequential(new CommandDelay(1));
        addSequential(new ShooterInterrupt());
    }
}
