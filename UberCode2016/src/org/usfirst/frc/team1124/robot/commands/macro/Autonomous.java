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
    	mode = DashboardConnection.Auto.getMode();
    	defense_type = DashboardConnection.Auto.getDefenseType();
    	defense_position = DashboardConnection.Auto.getDefensePosition();
    	
    	if(mode == AutoMode.Nothing || defense_position == AutoDefensePosition.SpyBox){
    		// do nothing
    	}else{
    		// do something!
    		switch(mode){
				case Nothing:
				break;
    			case GetToDefense:
    		    	getToDefense();
    			break;
    			case CrossDefense:
    				getToDefense();
    				crossDefense();
    			break;
				case ScoreHighGoal:
					getToDefense();
					crossDefense();
					turnTowardsApproxGoal();
					scoreHighGoal();
				break;
				case ScoreLowGoal:
				break;
    		}
    	}
    }
    
    private void getToDefense(){
    	addSequential(new AutoDrive(60, 60));
    }
    
    private void crossDefense(){
		switch(defense_type){
			case Moat:
			break;
			case Ramparts:
			break;
			case RockWall:
			break;
			case RoughTerrain:
			break;
			case SomethingElse:
			break;
		}
    }
    
    /** turn towards the goal depending on where we started so we can target it */
    private void turnTowardsApproxGoal(){
    	
    }
    
    private void scoreHighGoal(){
    	addSequential(new ScoreHighGoal());
    }
}
