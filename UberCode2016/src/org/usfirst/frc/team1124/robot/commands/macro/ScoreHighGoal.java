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
    	//Will we ever need to shoot other than when we are trying to score a high goal?
    	//I guess there's manual shooting, but will we ever really do that? If not, why not just combine the
    	//contents of Shoot to this?
    }
}
