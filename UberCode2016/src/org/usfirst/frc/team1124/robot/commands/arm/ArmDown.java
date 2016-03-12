package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Move the arm forward (down) until it hits the limit switch.
 */
public class ArmDown extends Command {

    public ArmDown() {
    	requires(Robot.arm_actuator);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_actuator.manual(0.6);
    }

    protected boolean isFinished() {
        return Robot.arm_actuator.getLimitSwitchStates()[1];
    }

    protected void end() {
    	Robot.arm_actuator.stop();
    }

    protected void interrupted() {
    	end();
    }
}
