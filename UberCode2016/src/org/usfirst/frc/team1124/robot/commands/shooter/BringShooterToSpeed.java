package org.usfirst.frc.team1124.robot.commands.shooter;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Shoots a ball at a specific rate (using PID)
 */
public class BringShooterToSpeed extends Command {
	private double setpoint;
	private double voltage;
	
	public static final double SPEED = 3730.0; //3700.0;
	public static final double MAX_RPM = 4100.0;
	private final double APPROX_TIME_TO_SPEED_UP = 3.0;
	
	private Timer manualTimer = new Timer();
	
	private boolean safetyTrippedFirstCall = true;
	
    public BringShooterToSpeed(){
    	requires(Robot.shooter_pid);
    	
    	this.setpoint = SPEED;
    	
    	setInterruptible(true);

    	voltage = SPEED / MAX_RPM;
    }
	
    public BringShooterToSpeed(double speed){
    	requires(Robot.shooter_pid);
    	
    	this.setpoint = speed;
    	
    	setInterruptible(true);

    	voltage = speed / MAX_RPM;
    }

    protected void initialize(){
    	Robot.shooter_pid.setSetpoint(setpoint);
    	
    	Robot.shooter_pid.enable();
    }
    
    public double getSetpoint(){
    	return this.setpoint;
    }

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

    protected boolean isFinished(){
    	if(Robot.shooter_pid.isSafetyTripped()){
    		return manualTimer.get() >= APPROX_TIME_TO_SPEED_UP;
    	}else{
    		return Math.abs(Robot.shooter_pid.getRate() - SPEED) < 50;
    	}
    }

    protected void end() {}

    protected void interrupted(){
    	Robot.shooter_pid.stop();
    }
}
