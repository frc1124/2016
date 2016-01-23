package org.usfirst.frc.team1124.robot.commands.teleop;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class ArmSetpoint extends Command {

	double setpoint;
    public ArmSetpoint(double sp)
    {
    	requires(Robot.arm_actuator_pid);
    	setpoint = sp;
    }

    protected void initialize()
    {
    	Robot.arm_actuator_pid.changeSetSetpoint(setpoint);
    }

    protected void execute()
    {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
