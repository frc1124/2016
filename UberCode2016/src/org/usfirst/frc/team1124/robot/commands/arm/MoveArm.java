package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.enums.ArmState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This command moves the arm to certain setpoints using joystick commands.
 */
public class MoveArm extends Command {

	// list of setpoints, can be amended of course
	private final double UP_SETPOINT = 0.00;
	private final double DOWN_SETPOINT = 0.00;
	private final double CLIMB_SETPOINT = 0.00;
	
	private double setpoint = 0;
	
	public MoveArm(ArmState state){
		requires(Robot.arm_actuator_pid);
		
		setInterruptible(true);
		
		switch(state){
			case Up:
				setpoint = UP_SETPOINT;
			break;
			case Down:
				setpoint = DOWN_SETPOINT;
			break;
			case Climb:
				setpoint = CLIMB_SETPOINT;
			break;
		}
	}
	
	protected void initialize(){
		Robot.arm_actuator_pid.enable();
    	Robot.arm_actuator_pid.setSetpoint(setpoint);
	}

	protected void execute(){}

	protected boolean isFinished(){
		return Robot.arm_actuator_pid.onTarget();
	}

	protected void end(){
		Robot.arm_actuator_pid.disable();
		
		Scheduler.getInstance().add(new ArmHoldPosition());
	}

	protected void interrupted(){
		Robot.arm_actuator_pid.disable();
	}
}
