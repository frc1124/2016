package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TimedAutoDrive extends Command {
	
	private double speed_left;
	private double speed_right;
	
    public TimedAutoDrive(double speed_left, double speed_right, double duration) {
        requires(Robot.drivetrain);
        
        setInterruptible(false);
        
        this.speed_left = speed_left;
        this.speed_right = speed_right;
        
        setTimeout(duration);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.drivetrain.drive_tank(speed_left, speed_right);
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
