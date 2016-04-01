package org.usfirst.frc.team1124.robot.commands.drive.targeting;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.tools.vision.VisionTools;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aim at an angle using the gyro.
 */
public class AimAtAnglePID extends PIDCommand {
	//w/o oscillation: 0.02464, w/ oscillation: 0.064, small angle: 0.111
	private static final double P = 0.08464;
	private static final double I = 0.00003;
	private static final double D = 0.057;
	
	public double voltage = 0;
	
	private double angle = 0;
	private double prevOutput = 0;
	
	private boolean gotToTarget = false;
	private boolean gotCloseToTarget = false;
	
	private Timer t = new Timer();
	private boolean timerFirstCall = true;
	
    public AimAtAnglePID() {
    	super("AimAtAngle", P, I, D);
        requires(Robot.drivetrain);
        
        setInterruptible(true);
        
        this.getPIDController().setOutputRange(-0.5, 0.5);
    }

    protected void initialize() {
    	double setpoint = 0;
    	gotToTarget = false;
    	
    	try{
	    	double xlhsGoalBBox = SmartDashboard.getNumber("vision_target_left");
	    	double widthGoalBBox = SmartDashboard.getNumber("vision_target_width");
	    	
	    	System.out.println("Target Left X: " + xlhsGoalBBox + " Width: " + widthGoalBBox);
	    	
	    	setpoint = VisionTools.turnAngle(xlhsGoalBBox, widthGoalBBox, true);
	    	
	    	System.out.println("Setpoint: " + setpoint);
    	}catch(Exception oh_no){
    		System.out.println("Fatal Targeting Error: Dashboard data not found.");
    	}
    	
    	Robot.drivetrain.resetGyro();
        
    	angle = setpoint;
        
        setSetpoint(angle);
        
        if(Math.abs(angle) <= 5){
        	double p_override = 0.102;
        	double i_override = 0.0004;
        	double d_override = 0.072;
        	
        	if(Math.signum(angle) < 0){
        		// things are different if left
        		d_override = 0.062;
        	}
        	
        	getPIDController().setPID(p_override, i_override, d_override);
        }else if(Math.signum(angle) < 0){
    		// things are different if left
        	double p_override = 0.1026;
        	double i_override = 0.00002;
        	double d_override = 0.05;

        	getPIDController().setPID(p_override, i_override, d_override);
        }
    }

    protected void execute() {
    	// used to be within 18.8 pixels
    	if((Math.abs(Robot.camera.getTargetCenterOfMass()[0] - 160) <= 25.0 && Math.abs(Robot.drivetrain.getRightEncoderRate()) <= 0.5) || gotCloseToTarget){
    		getToCloseTarget();
    		
    		gotCloseToTarget = true;
    	}else{
    		timerFirstCall = true;
	    	this.getPIDController().enable();
    	}
    }

	protected double returnPIDInput() {
    	return Robot.drivetrain.getFullAngle();
	}

	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive_tank_auto(output, -output);
	}
	
    protected boolean isFinished() {
    	return gotToTarget;
    }

    protected void end() {
    	Robot.drivetrain.stop();
    	
    	this.getPIDController().reset();
    	
    	timerFirstCall = true;
    	gotCloseToTarget = false;
    }

    protected void interrupted() {
    	end();
    }
	
	double output = 0;
	
	double offset;
	
	double acceleration = 0.08;
	double stop_voltage = 0.14;
    
    protected void getToCloseTarget(){
		double center = Robot.camera.getTargetCenterOfMass()[0];
		
    	if(timerFirstCall){
	    	this.getPIDController().disable();
	    	
	    	t.reset();
	    	t.start();
	    	
	    	timerFirstCall = false;
	    	
	    	gotToTarget = false;
			
			offset = 0.15 + (Math.abs(center - 160) * 0.01);
    	}
    	
    	System.out.println("offset: " + offset);
    	
    	if(center < 162.0 && center > 158.0){
			gotToTarget = true;
		}else if(center > 160.0){
			output = -((acceleration * t.get()) + offset);
		}else if(center < 160.0){
			output = (acceleration * t.get()) + offset;
		}
		
    	if(gotToTarget){
    		output = Math.signum(prevOutput) * stop_voltage;
    		voltage = output;
    	}else if(Math.abs(center - 160) < 5){
			output = Math.signum(output) * 0.25;
    	}else if(Math.abs(center - 160) < 10){
			output = Math.signum(output) * 0.32;
    	}else if(Math.abs(center - 160) < 50 && Math.abs(center - 160) > 20){
			output = Math.signum(output) * 0.65;
    	}
    	
    	if(Math.abs(output) > 0.45){
			output = Math.signum(output) * 0.45;
		}
    	
    	System.out.println("Output: " + output);
		
	    Robot.drivetrain.drive_tank_auto((-1) * output, output);
	    
	    prevOutput = output;
    }
}
