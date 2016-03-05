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
	
    public Autonomous() {
    	// legit code is for noobs
    }
    
    public void start(){
    	mode = DashboardConnection.Auto.getMode();
    	defense_type = DashboardConnection.Auto.getDefenseType();
    	defense_position = DashboardConnection.Auto.getDefensePosition();
    	
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
				if(defense_position == AutoDefensePosition.SpyBox){
					// just aim and stuff, no moving and stuff
					shootHighGoal();
				}else{
					getToDefense();
					crossDefense();
					
					turnTowardsApproxGoal();
					scoreHighGoal();
				}
			break;
			case ScoreLowGoal:
				// y?
			break;
		}
    	
    	super.start();
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
    
    /** TODO: turn towards the goal depending on where we started so we can target it */
    private void turnTowardsApproxGoal(){
    	addSequential(new AutoDrive(0, 0));
    }
    
    private void scoreHighGoal(){
    	addSequential(new ScoreHighGoal());
    }
    
    private void shootHighGoal(){
    	addSequential(new SpyBoxHighGoal());
    }
}
