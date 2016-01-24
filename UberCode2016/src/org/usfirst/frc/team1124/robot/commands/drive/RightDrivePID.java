package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * A PID command for the right drive train
 */
public class RightDrivePID extends PIDCommand {
	
	private double speed = 0;
	private double setpoint = 0;

    public RightDrivePID(double setpoint) {
		super("RightDrivePID", Robot.drivetrain.P, Robot.drivetrain.I, Robot.drivetrain.D);
		
        requires(Robot.drivetrain);
        setInterruptible(true);
        
        this.setpoint = setpoint;
    }

    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	
    	setSetpoint(setpoint);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return getPIDController().onTarget();
    }

    protected void end() {
    	Robot.drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }

	protected double returnPIDInput() {
		return Robot.drivetrain.getRightEncoderDistance();
	}

	protected void usePIDOutput(double output) {
		speed = output;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void updateSetpoint(double setpoint){
		setSetpoint(setpoint);
	}
}
