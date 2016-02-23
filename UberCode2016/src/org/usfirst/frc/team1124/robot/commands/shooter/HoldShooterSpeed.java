package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Ran after BringShooterToSpeed() to maintain speed. </br>
 * Stopped using the shooter interrupt.
 */
public class HoldShooterSpeed extends Command {
	private Timer manualTimer = new Timer();
	private double voltage;
	
	private boolean safetyTrippedFirstCall = true;

    public HoldShooterSpeed() {
    	requires(Robot.shooter_pid);
    	
    	setInterruptible(true);

    	voltage = BringShooterToSpeed.MAGIC_SPEED_THAT_ALWAYS_WORKS / BringShooterToSpeed.MAX_RPM;
    }

    protected void initialize() {}

    protected void execute() {
    	if(Robot.shooter_pid.isSafetyTripped() && safetyTrippedFirstCall){
    		Robot.shooter_pid.disable();
    		
    		manualTimer.start();
    		safetyTrippedFirstCall = false;
    	}
    	
    	if(Robot.shooter_pid.isSafetyTripped()){
        	Robot.shooter_pid.manual(voltage);
    	}
    }

    // this will get interrupted when the time is right
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shooter_pid.stop();
    }

    protected void interrupted() {
    	end();
    }
}
