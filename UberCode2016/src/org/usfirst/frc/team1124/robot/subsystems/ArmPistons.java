package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
     
/**
 * The two pistons that are used for climbing
 */
public class ArmPistons extends Subsystem {
	
	DoubleSolenoid piston;
	
	public ArmPistons(){
		super("ArmPistons");
		
		int a = Robot.configIO.getIntVal("arm_piston_a");
		int b = Robot.configIO.getIntVal("arm_piston_b");
		
		piston = new DoubleSolenoid(a, b);
	}

    public void initDefaultCommand() {}
    
    /** Extend the arm pistons */
    public void extend(){
    	piston.set(DoubleSolenoid.Value.kForward);
    }
    
    /** Retract the arm pistons */
    public void retract(){
    	piston.set(DoubleSolenoid.Value.kReverse);
    }
}

