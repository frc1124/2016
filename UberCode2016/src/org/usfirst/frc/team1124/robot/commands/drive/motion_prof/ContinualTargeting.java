package org.usfirst.frc.team1124.robot.commands.drive.motion_prof;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lock on to pixel location using gyro rate.
 */
public class ContinualTargeting extends Command {
	private double rate = 0;
	
	private AngularRatePID pid = new AngularRatePID();
	
    public ContinualTargeting() {
        requires(Robot.drivetrain);
        
        setInterruptible(true);
    }
    
    protected void initialize() {
    	pid.start();
    }
    
    protected void execute() {
		double center = Robot.camera.getTargetCenterOfMass()[0];
		
		if(center <= 161.0 && center >= 159.0){
			// targeted
			rate = 0.0;
		}else if(center > 160.0){
			rate = 10.0;
		}else if(center < 160.0){
			rate = -10.0;
		}
		
		double error = Math.abs(center - 160);
		
		if(error < 5.0){
			rate = Math.signum(rate) * 4.0;
		}else if(error < 10.0){
			rate = Math.signum(rate) * 6.0;
		}else if(error < 15.0){
			rate = Math.signum(rate) * 8.0;
		}
		
		pid.setSetpoint(rate);
		
		Robot.drivetrain.drive_tank_auto(pid.getOutput(), (-1) * pid.getOutput());
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    protected void end() {
    	pid.cancel();
    }
    
    protected void interrupted() {
    	end();
    }
}
