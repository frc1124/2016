package org.usfirst.frc.team1124.robot.commands.camera;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.enums.CameraSelect;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Select which camera to use; use this with driver control
 */
public class SelectCamera extends Command {
	
	private CameraSelect selection;
	
    public SelectCamera(CameraSelect selection) {
        requires(Robot.camera);
        
        setInterruptible(true);
        
        this.selection = selection;
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.camera.selectCamera(selection);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}
    
    protected void interrupted() {}
}
