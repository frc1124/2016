package org.usfirst.frc.team1124.robot.tools;

/** 
 * This is implimented in subsystems where safeties are used. 
 */
public interface Safe {
	/** Enables the safety */
	public void enableSafety();
	
	/** Disables the safety (allowing for uninterrupted control)*/
	public void disableSafety();
	
	/** Is the safety protecting activity? 
	 * @return safety enabled
	 */
	public boolean isSafetyEnabled();
	
	/** Was the safety triggered, disabling the system? 
	 * @return safety tripped
	 */
	public boolean isSafetyTripped();
	
	/** If an output exceeds the threshold, disable output if the encoder reads no rate 
	 * @param threshold the threshold from 0 to 1 
	 */
	public void setRateCutoffThreshold(double threshold);
	
	/** If an output exceeds the threshold, disable output if the encoder reads no rate 
	 * @return the threshold from 0 to 1
	 */
	public double getRateCutoffThreshold();
	
	/** Use this method to process the disired output and make sure it is safe
	 * @param output the output you want to write
	 * @return the safe output
	 */
	public double safeOutput(double output);
}
