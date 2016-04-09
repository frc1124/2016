package org.usfirst.frc.team1124.robot.commands.drive.motion_prof;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Control the angular rate of the robot (this file just does calculations).
 */
public class AngularRatePID extends PIDCommand {

	private static final double P = 0.006;
	private static final double I = 0.00265;
	private static final double D = 0.0;
	
	private double output = 0;
	
    public AngularRatePID() {
        // not using requires because using this as a function rather than a command because legitimacy
    	super(P, I, D);
    	
    	//requires(Robot.drivetrain);
    	
    	setInterruptible(true);
    	
    	setSetpoint(0.0);
    }
    
    public void setSetpoint(double sp){
    	pid().setSetpoint(sp);
    }
    
    public double getSetpoint(){
    	return pid().getSetpoint();
    }
    
    public double getOutput(){
    	return output;
    }
    
    private PIDController pid(){
    	return this.getPIDController();
    }

    protected void initialize() {
    	Robot.drivetrain.resetGyro();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	this.getPIDController().reset();
    }

    protected void interrupted() {
    	end();
    }

	protected double returnPIDInput() {
		return Robot.drivetrain.getAngularRate();
	}

	protected void usePIDOutput(double output) {
		this.output = output;
	}
}
