package org.usfirst.frc.team1124.robot.commands.drive.motion_prof;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Hold at 0 degrees/second
 */
public class FreezeDrive extends Command {
	private AngularRatePID pid = new AngularRatePID();

    public FreezeDrive() {
        requires(Robot.drivetrain);
        
        setInterruptible(true);
    }
    
    protected void initialize() {
    	pid.start();
    }

    protected void execute() {
		pid.setSetpoint(0.0);
		
		Robot.drivetrain.drive_tank_auto(pid.getOutput(), (-1) * pid.getOutput());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	pid.cancel();
    }

    protected void interrupted() {
    	end();
    }
}
