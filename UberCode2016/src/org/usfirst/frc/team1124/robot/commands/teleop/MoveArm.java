package org.usfirst.frc.team1124.robot.commands.teleop;
// luke's doing this and typing this, any problems? contact him at his human being
import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {

	public MoveArm()
	{
		requires(Robot.arm_actuator_pid);
	}
	protected void initialize() {}

	protected void execute()
	{
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
