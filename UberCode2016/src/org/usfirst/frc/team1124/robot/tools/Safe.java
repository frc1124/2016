package org.usfirst.frc.team1124.robot.tools;

/** 
 * This is implimented in subsystems where safeties are used. 
 */
public interface Safe {
	public void enableSafety();
	public void disableSafety();
	
	/** Is the safety protecting activity? 
	 * @return safety enabled
	 */
	public boolean isSafetyEnabled();
	
	/** Was the safety triggered, disabling the system? 
	 * @return safety tripped
	 */
	public boolean isSafetyTripped();
	
	/** Use this method to process the disired output and make sure it is safe
	 * @param output the output you want to write
	 * @return the safe output
	 */
	public double safeOutput(double output);
	
	public void setRateCutoffThreshold(double threshold);
	public double getRateCutoffThreshold();
}
