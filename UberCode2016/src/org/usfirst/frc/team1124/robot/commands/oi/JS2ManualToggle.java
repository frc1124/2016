package org.usfirst.frc.team1124.robot.commands.oi;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.intake.IntakeManual;
import org.usfirst.frc.team1124.robot.commands.interrupt.IntakeInterrupt;
import org.usfirst.frc.team1124.robot.commands.interrupt.ShooterInterrupt;
import org.usfirst.frc.team1124.robot.commands.shooter.ShooterManual;
import org.usfirst.frc.team1124.robot.enums.JoystickManualState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manages JS2 manual control
 */
public class JS2ManualToggle extends Command {
	
	private int button = 0;
	private JoystickManualState prevState;
	
    public JS2ManualToggle(int button) {
    	this.button = button;
    	
    	prevState = Robot.oi.getJS2ManState();
    	
    	setInterruptible(false);
    }

    protected void initialize() {
    	switch(button){
	    	case 3:
	    	case 4:
	    		Robot.oi.setJS2ManState(JoystickManualState.None);
	    	break;
	    	case 5:
	    		Robot.oi.setJS2ManState(JoystickManualState.IntakeWheels);
	    	break;
	    	case 6:
	    		Robot.oi.setJS2ManState(JoystickManualState.Shooter);
	    	break;
    	}
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	switch(Robot.oi.getJS2ManState()){
	    	case None:
	    		if(prevState == JoystickManualState.IntakeWheels){
		    		Command cancelIntake = new IntakeInterrupt();
		    		cancelIntake.start();
	    		}else if(prevState == JoystickManualState.Shooter){
		    		Command cancelShooter = new ShooterInterrupt();
		    		cancelShooter.start();
	    			
	    		}
	    	break;
	    	case IntakeWheels:
	    		Command intake = new IntakeManual();
	    		intake.start();
	    	break;
	    	case Shooter:
	    		Command shooter = new ShooterManual();
	    		shooter.start();
	    	break;
    		default:
    		break;
    	}
    }

    protected void interrupted() {
    	end();
    }
}
