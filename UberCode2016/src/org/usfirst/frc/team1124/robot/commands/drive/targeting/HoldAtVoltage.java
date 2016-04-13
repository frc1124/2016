package org.usfirst.frc.team1124.robot.commands.drive.targeting;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @deprecated
 * Hold a drive train voltage
 */
public class HoldAtVoltage extends Command {
	
	private double voltage = 0;
	private double error = 0;
	
	AimAtAnglePID cmd;
	
    public HoldAtVoltage(AimAtAnglePID cmd) {
        requires(Robot.drivetrain);
        
        this.cmd = cmd;
        
        setInterruptible(true);
    }

    protected void initialize() {
        voltage = cmd.voltage;
        
        /*
		error = Robot.camera.getTargetCenterOfMass()[0] - 160;
    	
    	System.out.println("Hold error: " + error);
		
		if(Math.signum(voltage) > 0){
			// if we want to drive positive so turn left
			if(error <= -2){
				voltage = 0;
			}else if(error >= 3){
				voltage = Math.abs(voltage) * -1;
			}
		}else{
			// if we want to drive negative so turn right
			if(error >= 2){
				voltage = 0;
			}else if(error <= -3){
				voltage = Math.abs(voltage) * 1;
			}
		}*/
    }

    protected void execute() {
    	Robot.drivetrain.drive_tank_auto((-1) * voltage, voltage);
    	
    	System.out.println("Hold Voltage: " + voltage);
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
