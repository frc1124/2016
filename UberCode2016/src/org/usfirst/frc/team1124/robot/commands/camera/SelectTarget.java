package org.usfirst.frc.team1124.robot.commands.camera;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Select which target to aim at
 */
public class SelectTarget extends Command {
	
	private boolean selectLeft;
	
    public SelectTarget(boolean selectLeft) {
        this.selectLeft = selectLeft;
        
        // init this smart dashboard data on boot/in the constructor
    	SmartDashboard.putBoolean("target_select", true);
    }

    protected void initialize() {}

    protected void execute() {
    	SmartDashboard.putBoolean("target_select", selectLeft);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
