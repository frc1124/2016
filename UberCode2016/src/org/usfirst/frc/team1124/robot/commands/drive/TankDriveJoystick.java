package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class TankDriveJoystick extends Command {
	
	public TankDriveJoystick(){
		requires(Robot.drivetrain);
        
        setInterruptible(true);
	}

	protected void initialize() {}

	protected void execute() {
		Joystick js = Robot.oi.getJS1();
		
		Robot.drivetrain.drive_tank(js);
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
