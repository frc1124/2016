package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.intake.IntakeIn;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Ramp down, intake wheels in. Attempt to un-jam the intake. </br>
 * This command is meant to be toggled manually, therefore it doesn't stop.
 */
public class RecoverIntakedBall extends CommandGroup {
    
    public RecoverIntakedBall() {
        addParallel(new RampBeltsFeedToIntake());
        addParallel(new IntakeIn());
    }
}
