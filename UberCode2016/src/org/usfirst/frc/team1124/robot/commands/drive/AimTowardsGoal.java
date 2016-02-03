package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;


/** 
 * 
 */
public class AimTowardsGoal extends Command {

	private double angleFromGoal = 0; // in radians
	private double distanceToTurn = 0;
	private AutoDrive turnCommand;
	private final double ROBOT_LENGTH = 0; // for distance purposes, set this plssss
	
    public AimTowardsGoal(double angle) // WHOEVER SEES THIS FOR REFERENCE, MAKE NEGATIVE ANGLES TO THE RIGHT AND POSITIVE TO THE LEFT (e.g. -.785 would mean 90 degrees to the right of the goal, but that better not happen)
    {
    	// take angle
    	// do wizard to find what distance to turn (PID)
    	// turn based on that.
    	
    	angleFromGoal = angle;
    	requires(Robot.drivetrain);
    }

    protected void initialize()
    {
    	distanceToTurn = (angleFromGoal/2)*Math.pow(ROBOT_LENGTH/2, 2); // if the math is different feel free to change lol
    	if (angleFromGoal < 0)
    	{
    		turnCommand = new AutoDrive(distanceToTurn, 0, true);
    	}
    	else if (angleFromGoal > 0)
    	{
    		turnCommand = new AutoDrive(0, distanceToTurn, true);
    	}
		Scheduler.getInstance().add(turnCommand);
    }

    protected void execute() {}

    protected boolean isFinished() {
    		return !turnCommand.isRunning();
    }

    protected void end() {}

    protected void interrupted() {}
}
