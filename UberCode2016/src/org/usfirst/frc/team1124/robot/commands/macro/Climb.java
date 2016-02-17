package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.arm.ArmPistonsRetract;
import org.usfirst.frc.team1124.robot.commands.arm.MoveArm;
import org.usfirst.frc.team1124.robot.enums.ArmState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Retract the pistons to lif the robot up and adjust the arm to hold us at the right angle.
 */
public class Climb extends CommandGroup {
    
    public Climb() {
        addSequential(new ArmPistonsRetract());
        
        // TODO tune this time
        addSequential(new CommandDelay(2.00));
        
        addSequential(new MoveArm(ArmState.Climb));
    }
}
