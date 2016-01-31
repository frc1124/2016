package org.usfirst.frc.team1124.robot.commands.steptest;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.tools.StepTest;
import org.usfirst.frc.team1124.robot.tools.StepTestPoint;
import edu.wpi.first.wpilibj.command.Command;
import java.util.*;

/**
 * StepTestDriveTrain
 * 
 * This command performs a step test on the drive train. It tracks the the left motors/left encoder and right motors/right encoders.
 * It logs data to a files and stores the data live for later analysis.
 * 
 * @author	Mrs. Weston
 */
public class StepTestDriveTrain extends Command {
	/**
	 * Test outputs for each step test.
	 */
	private double[] output = { 0.45,
								0.52,
								0.44,
								0.37,
								0.61
							  };
	/**
	 * Number of milliseconds for each step test.
	 */
	private long period = 5000;

	private int currentStep = 0;
	private PrintWriter leftLog;
	private PrintWriter rightLog;
	private long periodStart = 0;

	private StepTest leftData = new StepTest();
	private StepTest rightData = new StepTest();

	public StepTestDriveTrain() {
		requires(Robot.drivetrain);
	}

	/**
	 * Sets up for the test and data gathering.
	 */
	@Override
	protected void initialize() {
		try {
			// Initialize the step values
			periodStart = System.currentTimeMillis();

			// Open the logs
			SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd-hh-MM");
			String dt = df.format(new Date());
			this.leftLog = new PrintWriter("drivetrain-left-"+dt+".log");
			this.rightLog = new PrintWriter("drivetrain-right-"+dt+".log");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Perform the test. This is called back over and over until isFinished() returns true. 
	 * In this case, that means we've run out of steps.
	 */
	@Override
	protected void execute() {
		// Check to change period
		long c = System.currentTimeMillis();
		if (periodStart + this.period > c) {
			this.periodStart = c;
			this.leftData.changeSignal();
			this.rightData.changeSignal();
			this.currentStep++;
		}

		// If at the end, schedule a command to analyze the results
		if (this.currentStep > this.output.length) {
			return;
		}

		// Set the motors
		Robot.drivetrain.setLeftMotor(this.output[this.currentStep]);
		Robot.drivetrain.setRightMotor(-1*this.output[this.currentStep]);

		// Log the encoders
		double t = (c - this.periodStart)/1000; // Find the number of elapsed seconds for the period
		double lo = Robot.drivetrain.getLeftMotor();
		double le = Robot.drivetrain.getLeftEncoderDistance();
		this.leftLog.println(t+"\t"+lo+"\t"+le);

		double ro = Robot.drivetrain.getRightMotor();
		double re = Robot.drivetrain.getRightEncoderDistance();
		this.rightLog.println(t+"\t"+ro+"\t"+re);

		// Add to the live data
		this.leftData.addPoint(new StepTestPoint(t, ro, re));
		this.rightData.addPoint(new StepTestPoint(t, ro, re));
	}

	/**
	 * Check what step we're on to see if the test is over.
	 */
	@Override
	protected boolean isFinished() {
		return (currentStep > output.length);
	}

	/**
	 * Close the log. Calculate the coefficients for left and right sides.
	 */
	@Override
	protected void end() {
		// Close the logs
		this.leftLog.close();
		this.rightLog.close();
	}

	/**
	 * Close the log if interrupted.
	 */
	@Override
	protected void interrupted() {
		// Close the logs
		this.leftLog.close();
		this.rightLog.close();
	}

	public StepTest getLeftData() {
		return this.leftData;
	}

	public StepTest getRightData() {
		return this.rightData;
	}
}
