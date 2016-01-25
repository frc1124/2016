package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

//_____  _____  ______   ________  _______      _______  _____   ______   _________    ___   ____  _____   ______   
//|_   _||_   _||_   _ \ |_   __  ||_   __ \    |_   __ \|_   _|.' ____ \ |  _   _  | .'   `.|_   \|_   _|.' ____ \  
//| |    | |    | |_) |  | |_ \_|  | |__) |     | |__) | | |  | (___ \_||_/ | | \_|/  .-.  \ |   \ | |  | (___ \_| 
//| '    ' |    |  __'.  |  _| _   |  __ /      |  ___/  | |   _.____`.     | |    | |   | | | |\ \| |   _.____`.  
//\ \__/ /    _| |__) |_| |__/ | _| |  \ \_   _| |_    _| |_ | \____) |   _| |_   \  `-'  /_| |_\   |_ | \____) | 
// `.__.'    |_______/|________||____| |___| |_____|  |_____| \______.'  |_____|   `.___.'|_____|\____| \______.' 
//      
/**
 * The two pistons that are used for climbing
 */
public class ArmPistons extends Subsystem {
	
	DoubleSolenoid left_piston;
	DoubleSolenoid right_piston;
	
	public ArmPistons(){
		super("ArmPistons");
		
		int left_a = Robot.configIO.getIntVal("arm_piston_left_a");
		int left_b = Robot.configIO.getIntVal("arm_piston_left_b");
		int right_a = Robot.configIO.getIntVal("arm_piston_right_a");
		int right_b = Robot.configIO.getIntVal("arm_piston_right_b");
		
		left_piston = new DoubleSolenoid(left_a, left_b);
		right_piston = new DoubleSolenoid(right_a, right_b);
	}

    public void initDefaultCommand() {
        //setDefaultCommand(new Command());
    }
    
    /** Extend the arm pistons */
    public void extend(){
    	left_piston.set(DoubleSolenoid.Value.kForward);
    	right_piston.set(DoubleSolenoid.Value.kForward);
    }
    
    /** Retract the arm pistons */
    public void retract(){
    	left_piston.set(DoubleSolenoid.Value.kReverse);
    	right_piston.set(DoubleSolenoid.Value.kReverse);
    }
}

