package org.usfirst.frc.team1124.robot.commands.camera;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.enums.CameraSelect;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Select the intake camera for streaming. </br>
 * Must be ended using a command group; this should only be used for the auto code using the camera, not the driver selecting.
 */
public class StreamIntakeCamera extends Command {

    public StreamIntakeCamera() {
        requires(Robot.camera);
        
        setInterruptible(false);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.camera.selectCamera(CameraSelect.Intake);
    }

    // ended by owning command group
    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
