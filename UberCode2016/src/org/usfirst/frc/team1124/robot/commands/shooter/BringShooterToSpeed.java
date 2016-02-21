package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Shoots a ball at a specific rate (using PID)
 */
public class BringShooterToSpeed extends Command {
	
	private double setpoint;
	private double MAGIC_SPEED_THAT_ALWAYS_WORKS = 3800.0;
	
	private boolean safetyTrippedFirstCall = true;
	
	private Command fallback_command;
	
    public BringShooterToSpeed(){
    	requires(Robot.shooter_pid);
    	
    	this.setpoint = MAGIC_SPEED_THAT_ALWAYS_WORKS;
    	
    	setInterruptible(true);
    	
    	fallback_command = new BringShooterToApproxSpeed();
    }

    protected void initialize(){
    	Robot.shooter_pid.setSetpoint(setpoint);
    	
    	Robot.shooter_pid.enable();
    }

    protected void execute() {
    	if(Robot.shooter_pid.isSafetyTripped() && safetyTrippedFirstCall){
    		Robot.shooter_pid.disable();
    		
    		fallback_command.start();
    		
    		safetyTrippedFirstCall = false;
    	}
    }
    
    public boolean atSetpoint(){
    	return Robot.shooter_pid.getPIDController().getAvgError() <= 2.0;
    }

    protected boolean isFinished(){
    	if(Robot.shooter_pid.isSafetyTripped()){
    		return fallback_command.isRunning();
    	}else{
    		return false;
    	}
    }

    protected void end() {
    	fallback_command.cancel();
    	
    	Robot.shooter_pid.setSetpoint(0);
    	Robot.shooter_pid.manual(0);
    }

    protected void interrupted(){
    	end();
    }
}
