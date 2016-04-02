package org.usfirst.frc.team1124.robot.commands.macro;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.CommandDelay;
import org.usfirst.frc.team1124.robot.commands.arm.ArmDown;
import org.usfirst.frc.team1124.robot.commands.camera.SelectTarget;
import org.usfirst.frc.team1124.robot.commands.drive.TimedAutoDrive;
import org.usfirst.frc.team1124.robot.commands.drive.TurnOffCurrentAnglePID;
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
    	// legit code is for n00bz
    }
    
    public void start(){
    	Robot.drivetrain.resetGyro();
    	
    	mode = DashboardConnection.Auto.getMode();
    	defense_type = DashboardConnection.Auto.getDefenseType();
    	defense_position = DashboardConnection.Auto.getDefensePosition();
    	
		switch(mode){
			case Nothing:
				// do everything cause spite
				// import org.curren.scrub.spite;
				// doEverything();
			break;
			case GetToDefense:
				armDown();
				
		    	getToDefense();
			break;
			case CrossDefense:
				armDown();
				
				getToDefense();
				crossDefense();
			break;
			case ScoreHighGoal:
				if(defense_position == AutoDefensePosition.SpyBox){
					// just aim and stuff, no moving and stuff
					armDown();
					
					shootHighGoal();
				}else{
					armDown();
					
					getToDefense();
					crossDefense();
					
					turnTowardsApproxGoal();
					scoreHighGoal();
				}
			break;
			case ScoreLowGoal:
				// y? y do u do dis?
			break;
		}
    	
    	super.start();
    }
    
    private void armDown(){
    	addSequential(new ArmDown());
    }
    
    private void getToDefense(){
    	addSequential(new TimedAutoDrive(0.8, 0.8, 1.0));
    }
    
    private void crossDefense(){
		switch(defense_type){
			case Moat:
				//addSequential(new TimedAutoDrive(0.9, 0.9, 1.8));
				addSequential(new TimedAutoDrive(0.85, 0.9, 2.6));
				addSequential(new CommandDelay(0.2));
			break;
			case Ramparts:
				addSequential(new TimedAutoDrive(0.9, 0.9, 0.5));
				addSequential(new TimedAutoDrive(0.55, 1.0, 0.55));
				//addSequential(new TimedAutoDrive(0.9, 0.9, 0.9));
				addSequential(new TimedAutoDrive(0.9, 0.9, 1.2));
				addSequential(new CommandDelay(0.4));
			break;
			case RockWall:
				//addSequential(new TimedAutoDrive(0.8, 0.8, 2.1));
				addSequential(new TimedAutoDrive(0.8, 0.8, 2.6));
				addSequential(new CommandDelay(0.4));
			break;
			case RoughTerrain:
				//addSequential(new TimedAutoDrive(0.9, 0.9, 1.7));
				addSequential(new TimedAutoDrive(0.9, 0.9, 2.2));
				addSequential(new CommandDelay(0.2));
			break;
			case SomethingElse:
				// do nothing
			break;
		}
    }
    
    private void turnTowardsApproxGoal(){
    	switch(defense_position){
			case Pos_1:
				// THROUGH THE LOW BAR!!! MUHAHAHHAHAHAHA
				// destroyLowBar(DestroyType.violently);
				break;
			case Pos_2:
				addParallel(new SelectTarget(false));
				addSequential(new TurnOffCurrentAnglePID(24.0));
				break;
			case Pos_3:
				addParallel(new SelectTarget(false));
				addSequential(new TurnOffCurrentAnglePID(14.0));
				break;
			case Pos_4:
				addParallel(new SelectTarget(true));
				addSequential(new TurnOffCurrentAnglePID(0.0));
				break;
			case Pos_5:
				addParallel(new SelectTarget(true));
		    	addSequential(new TurnOffCurrentAnglePID(-18.0));
				break;
			case SpyBox:
				break;
		}
    	
    	// delay before targeting
		addSequential(new CommandDelay(0.3));
    }
    
    private void scoreHighGoal(){
    	// re-intake incase ball fell down a bit
    	addSequential(new IntakeBall());
    	
    	// shoot
    	addSequential(new ScoreHighGoal());
    }
    
    private void shootHighGoal(){
    	addSequential(new SpyBoxHighGoal());
    }
}
