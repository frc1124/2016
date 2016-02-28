package org.usfirst.frc.team1124.robot.commands.drive;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aim at an angle using the gyro.
 */
public class AimAtAngle extends PIDCommand {
	//w/o oscillation: 0.02464, w/ oscillation: 0.064, small angle: 0.111
	private static final double P = 0.028;
	private static final double I = 0.0;
	private static final double D = 0.018;
	
	private double angle = 0;
	
	/**
	 * @param angle angle in degrees
	 */
    public AimAtAngle() {
    	super("AimAtAngle", P, I, D);
        requires(Robot.drivetrain);
        
        this.getPIDController().setOutputRange(-0.5, 0.5);
        
        //double p_override = 0.111;
        /*
        if(angle >= 20){
        	p_override = 0.02884;
        }else if(angle < 20){
        	p_override = 0.064;
    	}else if(angle <= 8){
    		p_override = 0.111;
        }
        random i value 0.000456
        this.getPIDController().setPID(p_override, I, D);
        */
    }

    protected void initialize() {
    	Robot.drivetrain.resetGyro();
        
        angle = SmartDashboard.getNumber("ANGLE");
        
        setSetpoint(angle);
    }

    protected void execute() {
		try{
			double p = SmartDashboard.getNumber("P");
			double i = SmartDashboard.getNumber("I");
			double d = SmartDashboard.getNumber("D");
			
			if(SmartDashboard.getBoolean("SET_PID")){
				getPIDController().setPID(p, i, d);
				System.out.println(p + ", " + i + ", " + d);
			}
		}catch(Exception e){}
    }

	protected double returnPIDInput() {
    	return Robot.drivetrain.getFullAngle();
	}

	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("pid-output", output);
		Robot.drivetrain.drive_tank_auto(output, -output);
	}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }
}
