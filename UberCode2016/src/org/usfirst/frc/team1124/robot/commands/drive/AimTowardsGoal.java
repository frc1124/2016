package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;


/** 
 * The code to turn towards the goal.
 */
public class AimTowardsGoal extends CommandGroup {

	private double angleFromGoal = 0; // in radians
	private double distanceToTurn = 0;
	
	private AutoDrive turnCommand;
	
	private final double ROBOT_LENGTH = 0; // for distance purposes, set this plssss
	
	/** @param angle The angle the robot needs to turn; left is negative, right is positive */
    public AimTowardsGoal(double angle) {
    	angleFromGoal = angle;
    }

    protected void initialize() {
    	distanceToTurn = (angleFromGoal / 2) * Math.pow(ROBOT_LENGTH / 2, 2); // if the math is different feel free to change lol
    	
    	if (angleFromGoal < 0){
    		turnCommand = new AutoDrive(distanceToTurn, -distanceToTurn, true);
    	}else if(angleFromGoal > 0){
    		turnCommand = new AutoDrive(-distanceToTurn, distanceToTurn, true);
    	}else{
    		end();
    	}
    	
		addSequential(turnCommand);
    }

    protected void execute() {}

    protected boolean isFinished() {
    	return turnCommand.isFinished();
    }

    protected void end() {}

    protected void interrupted() {}
}
