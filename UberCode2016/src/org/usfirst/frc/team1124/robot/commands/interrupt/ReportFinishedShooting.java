package org.usfirst.frc.team1124.robot.commands.interrupt;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * In case the shooter cage sensor does not trigger
 */
public class ReportFinishedShooting extends CommandGroup {
    
    public  ReportFinishedShooting() {
        addSequential(new ShooterInterrupt());
        addSequential(new DriveTrainInterrupt());
    }
    
    protected void end(){
    	super.end();
    	
    	Robot.ramp_belts.removeBall();
    }
    
    protected void interrupted(){
    	super.interrupted();
    	
    	this.end();
    }
}
