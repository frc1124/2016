package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Use PID and pixel data to turn towards the target
 */
public class AimTowardsGoalPID extends PIDCommand {
	private static final double P = 0.0065;
	private static final double I = 0;
	private static final double D = 0;
	
	// p = 0.007914
	// i = 0
	// d = 0.000018031
	
	private static final int image_width = 320;	
	private static final int setpoint = image_width / 2;
	
    public AimTowardsGoalPID() {
    	super("AimTowardsGoalPID", P, I, D);
    	
    	requires(Robot.drivetrain);
    	setInterruptible(true);
    	
    	getPIDController().setToleranceBuffer(5);
    	
    	getPIDController().setOutputRange(-0.5, 0.5);
    }
    
	protected double returnPIDInput() {
		return Robot.camera.getTargetCenterOfMass()[0];
	}
	
	protected void usePIDOutput(double output) {
		// System.out.println(output + " setpoint: " + getSetpoint() + " process var: " + returnPIDInput());
		Robot.drivetrain.drive_tank_auto((-1) * output, output);
	}
	
	protected void execute() {
		double center = Robot.camera.getTargetCenterOfMass()[0];
		
		if(center == 0){
			getPIDController().disable();
			getPIDController().reset();
		}else{
			getPIDController().enable();
		}
		
		/*
		try{
			double p = SmartDashboard.getNumber("P");
			double i = SmartDashboard.getNumber("I");
			double d = SmartDashboard.getNumber("D");
			
			if(SmartDashboard.getBoolean("SET_PID")){
				getPIDController().setPID(p, i, d);
			}
		}catch(Exception e){}
		*/
	}
	
	protected void initialize() {
		setSetpoint(setpoint);
	}

	protected boolean isFinished() {
		return false; //getPIDController().getAvgError() <= 2.0;
	}
	
	protected void end() {
		getPIDController().disable();
		getPIDController().reset();
	}

	protected void interrupted() {
		end();
	}
}
