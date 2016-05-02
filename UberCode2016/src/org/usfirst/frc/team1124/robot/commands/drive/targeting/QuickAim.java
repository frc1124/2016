package org.usfirst.frc.team1124.robot.commands.drive.targeting;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.experimental.Aim;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Gotta go fast.
 */
public class QuickAim extends Command {
	
	private double x;
	private double speed = 0;
	
	private Aim aim;
	
    public QuickAim() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        x = Robot.camera.getTargetCenterOfMass()[0];
        
    	aim = new Aim(x);
    }

    protected void execute() {
    	speed = aim.getOutput();
    	Robot.drivetrain.drive_tank_auto((-1) * speed, speed);
    }

    protected boolean isFinished() {
        return aim.done();
    }

    protected void end() {
    	Robot.drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }
}
