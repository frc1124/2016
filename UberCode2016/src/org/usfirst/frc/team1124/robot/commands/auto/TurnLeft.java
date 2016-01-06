package org.usfirst.frc.team1124.robot.commands.auto;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnLeft extends Command {
	
	public TurnLeft(){
		requires(Robot.drivetrain);
		setTimeout(0.4);
	}
	
	protected void initialize() {
		// reset encoders
	}

	protected void execute() {
		Robot.drivetrain.drive_tank(-1, 1);
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
        Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}

}
