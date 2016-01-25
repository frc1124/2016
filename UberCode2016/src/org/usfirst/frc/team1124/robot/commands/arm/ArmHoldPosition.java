package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//_____  _____  ______   ________  _______          _       _______     ____    ____  
//|_   _||_   _||_   _ \ |_   __  ||_   __ \       / \     |_   __ \   |_   \  /   _| 
//| |    | |    | |_) |  | |_ \_|  | |__) |       / _ \      | |__) |    |   \/   |   
//| '    ' |    |  __'.  |  _| _   |  __ /       / ___ \     |  __ /     | |\  /| |   
// \ \__/ /    _| |__) |_| |__/ | _| |  \ \_   _/ /   \ \_  _| |  \ \_  _| |_\/_| |_  
//  `.__.'    |_______/|________||____| |___| |____| |____||____| |___||_____||_____| 
//    
/**
 *	This command holds the arm at the current setpoint; it is ran between MoveArm commands
 */
public class ArmHoldPosition extends Command {

    public ArmHoldPosition(){
		requires(Robot.arm_actuator_pid);
		
		setInterruptible(true);
    }

    // Lock us at the current setpoint
    protected void initialize(){
		Robot.arm_actuator_pid.enable();
    }

    protected void execute(){}
    
    // This keeps running until we are interrupted
    protected boolean isFinished(){
        return false;
    }

    protected void end(){
		Robot.arm_actuator_pid.disable();
    }

    protected void interrupted(){
    	end();
    }
}
