package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;
import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;
import org.usfirst.frc.team1124.robot.tools.Safe;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * A PID command for the left drive train
 */
public class LeftDrivePID extends PIDCommand implements Safe {
	private double speed = 0;
	private double setpoint = 0;
	
	private Timer safetyTimer = new Timer();
	private boolean timerFirstCall = true;
	private final double TIME_DELAY = 0.2;
	
	private boolean safetyEnabled = false;
	private boolean safetyTripped = false;
	private double rate_threshold = 0.112;

	/** TODO tune these */
	private static final double P = 0.025;
	private static final double I = 0.0000956;
	private static final double D = 0.0;

	/** TODO tune these */
	private static final double P_rotate = 0;
	private static final double I_rotate = 0;
	private static final double D_rotate = 0;
	
	public LeftDrivePID(double setpoint) {
		super("LeftDrivePID", P, I, D);
		
        setInterruptible(true);
        
        getPIDController().setOutputRange(-0.75, 0.75);
    	getPIDController().disable();
        
        this.setpoint = setpoint;
        
        enableSafety();
    }
	
	public LeftDrivePID(double setpoint, double minOutput, double maxOutput) {
		super("LeftDrivePID", P, I, D);
		
        setInterruptible(true);
        
        getPIDController().setOutputRange(minOutput, maxOutput);
    	getPIDController().disable();
        
        this.setpoint = setpoint;
        
        enableSafety();
    }

    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	
    	setSetpoint(setpoint);
    }

    protected void execute() {}

    protected boolean isSideFinished() {
        return Math.abs(Robot.drivetrain.getLeftEncoderDistance() - getSetpoint()) <= Robot.drivetrain.SETPOINT_TOLERANCE;
    }
    
    public void setToRotatePID(){
    	getPIDController().setPID(P_rotate, I_rotate, D_rotate);
    	getPIDController().disable();
    }
    
    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
    	Robot.drivetrain.stop();
    	
    	System.out.println("left drive command ended");
    }

    protected void interrupted() {
    	end();
    }
    
	protected double returnPIDInput() {
		return Robot.drivetrain.getLeftEncoderDistance();
	}

	protected void usePIDOutput(double output) {
		if(isSafetyEnabled()){
			speed = safeOutput(output);
		}else{
			speed = output;
		}
	}
	
	public PIDController getPID(){
		return this.getPIDController();
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void updateSetpoint(double setpoint){
		setSetpoint(setpoint);
		safetyTimer.reset();
	}

	// Safeties
	
	public void enableSafety() {
		safetyEnabled = true;
		safetyTimer.reset();
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
		double safeOutput = output;
		
		if(timerFirstCall){
			safetyTimer.start();
			
			timerFirstCall = false;
		}
		
		// rate safeties
		if(Math.abs(Robot.drivetrain.getLeftEncoderRate()) > Double.MAX_VALUE / 4){
			// encoder was disconnected and is reading something around infinity
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.DriveTrainLeft, SafetyError.HighRateDisconnection);
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.DriveTrainLeft, SafetyError.HighRateDisconnection);
		}
		
		if(Math.abs(output) > getRateCutoffThreshold() && Robot.drivetrain.getLeftEncoderRate() == 0 && safetyTimer.get() >= TIME_DELAY){
			// we are moving it but the encoder isn't reading it, not good
			safeOutput = 0;
			safetyTripped = true;
			
			SafetyErrorLogger.log(SafetySubsystem.DriveTrainLeft, SafetyError.NoRateDisconnection);
		}else if(safetyTimer.get() >= TIME_DELAY){
			safetyTimer.reset();
		}else{
			SafetyErrorLogger.reportNoError(SafetySubsystem.DriveTrainLeft, SafetyError.NoRateDisconnection);
		}
		
		// permanent disable if safety is tripped
		if(safetyTripped){
			safeOutput = 0;
		}
		
		return safeOutput;
	}
}
