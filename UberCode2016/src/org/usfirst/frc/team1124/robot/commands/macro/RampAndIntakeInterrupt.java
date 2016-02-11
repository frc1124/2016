package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.interrupt.IntakeInterrupt;
import org.usfirst.frc.team1124.robot.commands.interrupt.RampBeltsInterrupt;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Interrupt both systems.
 */
public class RampAndIntakeInterrupt extends CommandGroup {
    
    public RampAndIntakeInterrupt() {
        addParallel(new RampBeltsInterrupt());
        addParallel(new IntakeInterrupt());
    }
}
