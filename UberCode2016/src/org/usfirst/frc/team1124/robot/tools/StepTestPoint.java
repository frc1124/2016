package org.usfirst.frc.team1124.robot.tools;

public class StepTestPoint {
	private double timestamp;
	private double output;
	private double value;

	public StepTestPoint() {
	}

	public StepTestPoint(double timestamp, double output, double value) {
		this.setTimestamp(timestamp);
		this.setOutput(output);
		this.setValue(value);
	}

	/**
	 * @return the timestamp
	 */
	public double getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the output
	 */
	public double getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(double output) {
		this.output = output;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
