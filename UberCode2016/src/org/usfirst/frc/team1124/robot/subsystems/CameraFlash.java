package org.usfirst.frc.team1124.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraFlash extends Subsystem {
    
	private Relay flash;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public CameraFlash(){
		super("CameraFlash");
		
		flash = new Relay(0);
		off();
	}
	
    public void initDefaultCommand() {
    }
    
    public void on(){
    	flash.set(Relay.Value.kForward);
    }
    
    public void off(){
    	flash.set(Relay.Value.kOff);
    }
}

