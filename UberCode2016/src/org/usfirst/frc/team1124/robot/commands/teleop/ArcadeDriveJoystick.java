package org.usfirst.frc.team1124.robot.commands.teleop;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDriveJoystick extends Command {
	
	public ArcadeDriveJoystick(){
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {}

	protected void execute() {
		Joystick js = Robot.oi.getJoystick();
		
		Robot.drivetrain.drive(js);
	}

	protected boolean isFinished() {
		// this doesn't ever stop (as of now) which is what we want...as of now...
		return false;
	}

	protected void end() {
        Robot.drivetrain.stop();
	}
	
	protected boolean isInterruptable(){
		// so we can toggle drive modes
		return true;
	}

	protected void interrupted() {
		end();
	}
}
