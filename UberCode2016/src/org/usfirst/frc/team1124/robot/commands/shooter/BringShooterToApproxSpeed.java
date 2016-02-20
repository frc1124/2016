package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Use voltage instead of a PID to control the shooter. </br>
 * ONLY USE IF THE SHOOTER ENCODER IS BROKEN/DISCONNECTED!!!!!
 */
public class BringShooterToApproxSpeed extends Command {
	
	private double voltage = 0.00;
	private final double MAX_RPM = 3680.0;

    public BringShooterToApproxSpeed() {
    	/**
    	 *  this runs during BringShooterToSpeed since when that ends it moves on, so this has 
    	 *  to not require anything and just run as a child of BringShooterToSpeed
    	 */
    	// requires(Robot.shooter_pid);
    	
    	// convert RPM back to voltage
    	voltage = Robot.camera.getCalculatedShooterRPM() / MAX_RPM;
    	
    	setInterruptible(true);
    }

    protected void initialize() {
    	Robot.shooter_pid.stop();
    }

    protected void execute() {
    	Robot.shooter_pid.manual(voltage);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shooter_pid.manual(0);
    	Robot.shooter_pid.resume();
    }

    protected void interrupted() {
    	end();
    }
}
