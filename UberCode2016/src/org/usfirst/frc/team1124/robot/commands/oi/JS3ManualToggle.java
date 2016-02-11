package org.usfirst.frc.team1124.robot.commands.oi;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.interrupt.RampBeltsInterrupt;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsManual;
import org.usfirst.frc.team1124.robot.enums.JoystickManualState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manages JS3 manual control
 */
public class JS3ManualToggle extends Command {
	
	private int button = 0;

    public JS3ManualToggle(int button) {
    	this.button = button;
    	
    	setInterruptible(false);
    }

    protected void initialize() {
    	switch(button){
	    	case 3:
	    		Robot.oi.setJS3ManState(JoystickManualState.None);
	    	break;
	    	case 5:
	    		Robot.oi.setJS3ManState(JoystickManualState.RampBelts);
	    	break;
    	}
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	switch(Robot.oi.getJS3ManState()){
	    	case None:
	    		Command beltsCancel = new RampBeltsInterrupt();
	    		beltsCancel.start();
	    	break;
	    	case RampBelts:
	    		Command belts = new RampBeltsManual();
	    		belts.start();
	    	break;
			default:
			break;
    	}
    }

    protected void interrupted() {
    	end();
    }
}
