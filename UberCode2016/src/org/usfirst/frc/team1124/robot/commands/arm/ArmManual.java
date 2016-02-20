package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manual control of the arm. </br>
 * Uses joystick 3, Y axis
 */
public class ArmManual extends Command {

    public ArmManual() {
        requires(Robot.arm_actuator_pid);
        
        setInterruptible(true);
    }

    protected void initialize() {
    	Robot.arm_actuator_pid.disable();
    }

    protected void execute() {
    	Robot.arm_actuator_pid.manual(Robot.oi.getJS3().getY());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.arm_actuator_pid.enable();
    }

    protected void interrupted() {
    	end();
    }
}
