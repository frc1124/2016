package org.usfirst.frc.team1124.robot.commands.belts;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
                                                                                                
/**
 *	Ball intake. Belts will run at intake speed set in IntakeBelts subsystem, then stop when light sensor detects ball.
 */
public class IntakeBall extends Command {

    public IntakeBall() {
        requires(Robot.intake);
        
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.intake.intake();
    }

    protected boolean isFinished() {
        return Robot.intake.getBallDetected();
    }

    protected void end(){
    	Robot.intake.stop();
    }

    protected void interrupted() {
    	end();
    }
}
