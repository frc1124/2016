package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Turn based off of CURRENT gyro angle; doesn't re-home it.
 */
public class TurnOffCurrentAnglePID extends PIDCommand {
    private static final double P = 0.08464;
	private static final double I = 0.00003;
	private static final double D = 0.057;
	
	private double angle = 0;

    public TurnOffCurrentAnglePID(double angle) {
    	super("AimAtAngle", P, I, D);
        requires(Robot.drivetrain);
        
        this.angle = angle;
        
        this.getPIDController().setOutputRange(-0.5, 0.5);
        
        setInterruptible(false);
    }

    protected void initialize() {
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
        	double p_override = 0.0826;
        	double i_override = 0.00002;
        	double d_override = 0.05;

        	getPIDController().setPID(p_override, i_override, d_override);
        }
    }
    
    protected void execute() {
    	
    }

	protected double returnPIDInput() {
		System.out.println(Robot.drivetrain.getFullAngle());
    	return Robot.drivetrain.getFullAngle();
	}

	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive_tank_auto(output, -output);
	}
	
    protected boolean isFinished() {
    	return Math.abs(Robot.drivetrain.getFullAngle()) >= Math.abs(angle) - 2 && Math.abs(Robot.drivetrain.getFullAngle()) <= Math.abs(angle) + 2;
    }
    
    protected void end() {
    	Robot.drivetrain.stop();
    	
    	this.getPIDController().reset();
    }
    
    protected void interrupted() {
    	end();
    }
}
