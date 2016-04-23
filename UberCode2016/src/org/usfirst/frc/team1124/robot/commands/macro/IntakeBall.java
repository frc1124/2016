package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.shooter.BringShooterToSpeed;

import edu.wpi.first.wpilibj.command.Command;
                                                                                                
/**
 *	Ball intake. Belts/wheels will run at intake speed, then stop when light sensor detects ball.
 */
public class IntakeBall extends Command {
	
    public IntakeBall() {
        requires(Robot.arm_intake_wheels);
        requires(Robot.ramp_belts);
        
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_intake_wheels.intake();
    	Robot.ramp_belts.intake();
    }

    protected boolean isFinished() {
    	// stop if sensor is false but had detected a ball or if we already have a ball
        return Robot.ramp_belts.getBallDetected() || Robot.ramp_belts.getBallCount() > 0;
    }

    protected void end() {
    	Robot.arm_intake_wheels.stop();
    	Robot.ramp_belts.stop();
    	
    	// initiate the shooter after we have finished intaking a ball
    	BringShooterToSpeed shooter_cmd = new BringShooterToSpeed();
    	shooter_cmd.start();
    	
    	Robot.ramp_belts.addBall();
    }

    protected void interrupted() {
    	Robot.arm_intake_wheels.stop();
    	Robot.ramp_belts.stop();
    }
}
