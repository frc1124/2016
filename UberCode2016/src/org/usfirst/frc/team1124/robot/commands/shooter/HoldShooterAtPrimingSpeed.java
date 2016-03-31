package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Bring shooter to the steady speed
 */
public class HoldShooterAtPrimingSpeed extends Command {

	private static final double SPEED = 3575.0;
	
    public HoldShooterAtPrimingSpeed() {
        requires(Robot.shooter_pid);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	Robot.shooter_pid.setSetpoint(SPEED);
    }

    protected void execute() {
    	Robot.shooter_pid.enable();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	// don't really do anything, something must have taken over, let it go [let it gooo! can't hold the code back anymoree!]
    }

    protected void interrupted() {
    	end();
    }
}
