package org.usfirst.frc.team1124.robot.commands.camera;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Select which camera to stream (instead of using switch on dashboard). </br>
 * Still is overriden by camera.setHeld(true).
 */
public class SelectCamera extends Command {

	private boolean shooter = true; 
	
    public SelectCamera(boolean initialValue) {
        this.shooter = initialValue;
        
        // init this smart dashboard data with the constructor
    	SmartDashboard.putBoolean("camera_select", initialValue);
    }

    protected void initialize() {}

    protected void execute() {
	shooter = !shooter;
    	SmartDashboard.putBoolean("camera_select", shooter);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}
    
    protected void interrupted() {}
}
