package org.usfirst.frc.team1124.robot.commands.steptest;

import org.usfirst.frc.team1124.robot.tools.StepTest;
import edu.wpi.first.wpilibj.command.Command;

/**
 * StepTestDriveTrain
 * 
 * This command analyzes step data in the form of a series of steps.
 * Data points are represented as time in seconds, output, and value.
 * 
 * Derived based on: http://blog.opticontrols.com/archives/697
 * 
 * Ziegler-Nichols Open-Loop Tuning Method:
 * https://controls.engin.umich.edu/wiki/index.php/PIDTuningClassical#Ziegler-Nichols_Open-Loop_Tuning_Method_or_Process_Reaction_Method
 * 
 * It would be best to have tests to determine if futher adjustments to coefficients are needed.
 * 
 * @author	Mrs. Weston
 */
public class CreatePIDFromStepTest extends Command {
	private StepTest data = null;
	private Double p = null;
	private Double i = null;
	private Double d = null;

	public CreatePIDFromStepTest(StepTest data) {
		this.data = data;
	}

	@Override
	protected void initialize() {
	}

	/**
	 * Analyze the step data to calculate the PID coefficients.
	 */
	@Override
	protected void execute() {
		this.p = this.data.getP();
		this.i = this.data.getI();
		this.d = this.data.getD();
	}

	/**
	 * Check if the PID coefficients have been calculated.
	 */
	@Override
	protected boolean isFinished() {
		return (this.p != null && this.i != null && this.d != null);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	public double getP() {
		return this.p;
	}

	public double getI() {
		return this.i;
	}

	public double getD() {
		return this.d;
	}

	/**
	 * Tests the analysis portion of the command.
	 */
	public void main(String[] args) {
		StepTest data = new StepTest();
		// TODO: fake data to test
		CreatePIDFromStepTest test = new CreatePIDFromStepTest(data);
		test.execute();

		// check coefficients
		System.out.println("P: "+test.getP());
		System.out.println("I: "+test.getI());
		System.out.println("D: "+test.getD());
	}
}
