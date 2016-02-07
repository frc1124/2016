package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.drive.AimTowardsGoalPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Aim and shoot the ball.
 */
public class ScoreHighGoal extends CommandGroup {
    
    public  ScoreHighGoal() {
    	addSequential(new AimTowardsGoalPID());
    	
    	addSequential(new Shoot());
    }
}
