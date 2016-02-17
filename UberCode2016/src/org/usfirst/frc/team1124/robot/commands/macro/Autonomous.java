package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.commands.drive.AutoDrive;
import org.usfirst.frc.team1124.robot.dashboard.DashboardConnection;
import org.usfirst.frc.team1124.robot.enums.AutoDefensePosition;
import org.usfirst.frc.team1124.robot.enums.AutoDefenseType;
import org.usfirst.frc.team1124.robot.enums.AutoMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous command, manages selecting what to do and then doing it.
 */
public class Autonomous extends CommandGroup {
	private AutoMode mode;
	private AutoDefenseType defense_type;
	private AutoDefensePosition defense_position;
	
    public  Autonomous() {
    	addSequential(new AutoDrive(60, 60));
    	
    	mode = DashboardConnection.Auto.getMode();
    	defense_type = DashboardConnection.Auto.getDefenseType();
    	defense_position = DashboardConnection.Auto.getDefensePosition();
    	
    	if(mode == AutoMode.Nothing || defense_type == AutoDefenseType.SomethingElse || defense_position == AutoDefensePosition.SpyBox){
    		// do nothing
    	}else{
    		// do something!
    		switch(mode){
    			default:
    		}
    	}
    }
}
