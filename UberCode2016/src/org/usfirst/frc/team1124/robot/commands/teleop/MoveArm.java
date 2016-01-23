package org.usfirst.frc.team1124.robot.commands.teleop;
// luke's doing this and typing this, any problems? contact him at his human being
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {

	double up_setpoint, down_setpoint, climb_setpoint; // list of setpoints, can be amended of course
	public MoveArm()
	{
		requires(Robot.arm_actuator_pid);
		up_setpoint = 0; // THIS WILL HAVE A VALUE WHEN DETERMINED
		down_setpoint = 0; // THIS WILL HAVE A VALUE WHEN DETERMINED
		climb_setpoint = 0; // THIS WILL HAVE A VALUE WHEN DETERMINED
	}
	protected void initialize()
	{
	}

	protected void execute()
	{
		Robot.arm_actuator_pid.setSetpoint(Robot.arm_actuator_pid.returnSetSetpoint());
		Robot.oi.getButton10().whenPressed(new ArmSetpoint(up_setpoint)); // BUTTON/SETPOINT SUBJECT TO CHANGE
		Robot.oi.getButton11().whenPressed(new ArmSetpoint(down_setpoint)); // BUTTON/SETPOINT SUBJECT TO CHANGE
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void end()
	{
	}

	protected void interrupted()
	{
	}

}
