package org.usfirst.frc.team1124.robot.commands.drive.motion_prof;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Lock on to pixel location using gyro rate.
 */
public class LockOnToPixelTarget extends Command {
	private boolean gotToTarget = false;
	
	private double rate = 0;
	
	private AngularRatePID pid = new AngularRatePID();
	
    public LockOnToPixelTarget() {
        requires(Robot.drivetrain);
        
        setInterruptible(true);
    }
    
    protected void initialize() {
    	pid.start();
    }
    
    protected void execute() {
		double center = Robot.camera.getTargetCenterOfMass()[0];
		
		if(center < 161.0 && center > 159.0){
			gotToTarget = true;
		}else if(center > 160.0){
			rate = 10.0;
		}else if(center < 160.0){
			rate = -10.0;
		}
		
		if(Math.abs(center - 160.0) < 5.0){
			rate = Math.signum(rate) * 6.0;
		}
		
		pid.setSetpoint(rate);
		
		Robot.drivetrain.drive_tank_auto(pid.getOutput(), (-1) * pid.getOutput());
    }
    
    protected boolean isFinished() {
        return gotToTarget;
    }
    
    protected void end() {
    	pid.cancel();
    }
    
    protected void interrupted() {
    	end();
    }
}
