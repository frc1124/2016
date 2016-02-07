package org.usfirst.frc.team1124.robot.dashboard;

import org.usfirst.frc.team1124.robot.enums.SafetyError;
import org.usfirst.frc.team1124.robot.enums.SafetySubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafetyErrorLogger {
	private static boolean[][] errorStates = new boolean[5][4];
	
	public static void log(SafetySubsystem subsystem, SafetyError error){
		int subsystem_id = subsystem.ordinal();
		int error_id = error.ordinal();
		
		boolean hadError = errorStates[subsystem_id][error_id];
		
		if(!hadError){
			errorStates[subsystem_id][error_id] = true;
			
			SmartDashboard.putBoolean("safety_error_" + subsystem_id + "_" + error_id, true);
		}
	}
	
	public static void reportNoError(SafetySubsystem subsystem, SafetyError error){
		errorStates[subsystem.ordinal()][error.ordinal()] = false;

		SmartDashboard.putBoolean("safety_error_" + subsystem.ordinal() + "_" + error.ordinal(), false);
	}
	
	public static void init(){
		for(int x = 0; x < errorStates.length; x++){
			for(int y = 0; y < errorStates[0].length; y++){
				SmartDashboard.putBoolean("safety_error_" + x + "_" + y, false);
			}
		}
	}
}
