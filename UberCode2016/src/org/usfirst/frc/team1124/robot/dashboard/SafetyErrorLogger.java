package org.usfirst.frc.team1124.robot.dashboard;

public class SafetyErrorLogger {
	public static void log(SafetySubsystem subsystem, Error error){
		long timestamp = System.currentTimeMillis();
		
		int id = subsystem.ordinal();
		int error_id = error.ordinal();
		
		
	}
	
	public enum Error {
		LimitSwitchDirection, EncoderDirection, HighRateDisconnection, NoRateDisconnection
	}
	
	public enum SafetySubsystem {
		DriveTrain, ArmActuator, RampBelts, Shooter
	}
}
