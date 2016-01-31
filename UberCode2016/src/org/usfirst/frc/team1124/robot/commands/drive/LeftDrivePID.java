package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.Error;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A PID command for the left drive train
 */
public class LeftDrivePID extends PIDCommand implements Safe {
	
	private double speed = 0;
	private double setpoint = 0;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	private double rate_threshold = 0.266;
	
	private static final double P = 0.0545; //0.5584615089283822
	private static final double I = 0.012; //1.21
	private static final double D = 0.0; //0.0968
	
	public LeftDrivePID(double setpoint) {
		super("LeftDrivePID", P, I, D);
		
        setInterruptible(true);
        
        getPIDController().setAbsoluteTolerance(Robot.drivetrain.SETPOINT_TOLERANCE);
        getPIDController().setOutputRange(-0.75, 0.75);
        
        this.setpoint = setpoint;
        
        enableSafety();
    }
	
	public LeftDrivePID(double setpoint, double minOutput, double maxOutput) {
		super("LeftDrivePID", P, I, D);
		
        setInterruptible(true);
        
        getPIDController().setAbsoluteTolerance(Robot.drivetrain.SETPOINT_TOLERANCE);
        getPIDController().setOutputRange(minOutput, maxOutput);
        
        this.setpoint = setpoint;
        
        enableSafety();
    }

    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	
    	setSetpoint(setpoint);
    }

    protected void execute() {
    	SmartDashboard.putNumber("l_avg_error", getPID().getAvgError());
    }

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
		return Robot.drivetrain.getLeftEncoderDistance();
	}

	protected void usePIDOutput(double output) {
		speed = output;
	}
	
	public PIDController getPID(){
		return this.getPIDController();
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void updateSetpoint(double setpoint){
		setSetpoint(setpoint);
	}

	// Safeties
	
	public void enableSafety() {
		safetyEnabled = true;
	}

	public void disableSafety() {
		safetyEnabled = false;
	}

	public boolean isSafetyEnabled() {
		return safetyEnabled;
	}

	public boolean isSafetyTripped() {
		return safetyTripped;
	}

	public void setRateCutoffThreshold(double threshold) {
		rate_threshold = threshold;
	}

	public double getRateCutoffThreshold() {
		return rate_threshold;
	}

	public double safeOutput(double output) {
		double safeOutput = 0;
		
		// rate safeties
		if(Math.abs(Robot.drivetrain.getLeftEncoderRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.DriveTrainLeft, Error.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.DriveTrainLeft, Error.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && Robot.drivetrain.getLeftEncoderRate() == 0){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.DriveTrainLeft, Error.NoRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.DriveTrainLeft, Error.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
