package org.usfirst.frc.team1124.robot.dashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SafetyErrorLogger {
	private static boolean[][] errorStates = new boolean[5][4];
	
	public static void log(SafetySubsystem subsystem, Error error){
		int subsystem_id = subsystem.ordinal();
		int error_id = error.ordinal();
		
		boolean hadError = errorStates[subsystem_id][error_id];
		
		if(!hadError){
			errorStates[subsystem_id][error_id] = true;
			
			SmartDashboard.putBoolean("safety_error_" + subsystem_id + "_" + error_id, true);
		}
	}
	
	public static void reportNoError(SafetySubsystem subsystem, Error error){
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
	
	public enum Error {
		LimitSwitchDirection, EncoderDirection, HighRateDisconnection, NoRateDisconnection
	}
	
	public enum SafetySubsystem {
		DriveTrainLeft, DriveTrainRight, ArmActuator, RampBelts, Shooter
	}
}
