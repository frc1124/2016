package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.intake.LowGoal;
import org.usfirst.frc.team1124.robot.commands.ramp.RampBeltsFeedToIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Score a low goal
 */
public class ScoreLowGoal extends CommandGroup {
    
    public ScoreLowGoal() {
        addParallel(new RampBeltsFeedToIntake());
        addParallel(new LowGoal());
    }
}
