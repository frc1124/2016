package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The two pistons that control the arm
 */
public class ArmPistons extends Subsystem {
	
	Solenoid left_piston;
	Solenoid right_piston;
	
	public ArmPistons(){
		super("ArmPistons");
		
		left_piston = new Solenoid(0);
		right_piston = new Solenoid(1);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

