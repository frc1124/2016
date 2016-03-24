package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Toggle the piston states from extended to retracted.
 */
public class TogglePistonState extends Command {

	private Value position;
	
    public TogglePistonState() {
        requires(Robot.arm_pistons);
    }

    protected void initialize() {
    	position = Robot.arm_pistons.getState();
    }

    protected void execute() {
    	switch(position){
			case kForward:
				Robot.arm_pistons.retract();
			break;
			case kReverse:
				Robot.arm_pistons.extend();
			break;
			default:
				// confused
			break;
    	}
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
