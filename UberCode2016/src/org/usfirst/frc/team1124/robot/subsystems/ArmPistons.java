package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
     
/**
 * The two pistons that are used for climbing
 */
public class ArmPistons extends Subsystem {
	private DoubleSolenoid piston;
	
	public ArmPistons(){
		super("ArmPistons");
		
		int a = RobotMap.ARM_PISTON_A;
		int b = RobotMap.ARM_PISTON_B;
		
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
    
    /** Get the piston state */
    public Value getState(){
    	return piston.get();
    }
}
