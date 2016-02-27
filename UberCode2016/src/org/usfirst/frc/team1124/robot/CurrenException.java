package org.usfirst.frc.team1124.robot;

public class CurrenException extends RuntimeException {
	public CurrenException() { super(); }
	public CurrenException(String message) { super(message); };
	public CurrenException(String message, Throwable cause) { super(message, cause); }
	public CurrenException(Throwable cause) { super(cause); }	
}
