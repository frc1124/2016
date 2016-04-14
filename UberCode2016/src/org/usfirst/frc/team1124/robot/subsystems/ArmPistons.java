package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
     
/**
 * The two pistons that are used for climbing
 */
public class ArmPistons extends Subsystem {
	
	DoubleSolenoid piston;
	
	public ArmPistons(){
		super("ArmPistons");
		
		int a = 0;
		int b = 1;
		
		piston = new DoubleSolenoid(a, b);
	}

    public void initDefaultCommand() {}
    
    /** Extend the arm pistons */
    public void extend(){
    	//if(120.0 - Timer.getMatchTime() <= 20){
    		piston.set(DoubleSolenoid.Value.kForward);
    	//}
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

