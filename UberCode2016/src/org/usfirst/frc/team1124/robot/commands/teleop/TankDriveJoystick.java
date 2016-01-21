package org.usfirst.frc.team1124.robot.commands.teleop;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class TankDriveJoystick extends Command {

	protected void initialize() {
		
	}

	protected void execute() {
		Joystick js = Robot.oi.getController();
		
		Robot.drivetrain.drive_tank(js);
	}

	protected boolean isFinished() {
		// this doesn't ever stop (as of now) which is what we want...as of now...
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		end();
		
	}

}
