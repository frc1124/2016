package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.arm.ArmPistonsExtend;
import org.usfirst.frc.team1124.robot.commands.arm.MoveArm;
import org.usfirst.frc.team1124.robot.enums.ArmState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This function sets us up to climb, then driver 1 adjusts us to be attached, then driver 2 runs Climb()
 */
public class ExtendToClimb extends CommandGroup {
    
    public ExtendToClimb() {
    	addSequential(new MoveArm(ArmState.Up));
    	
    	addSequential(new ArmPistonsExtend());
    }
}
