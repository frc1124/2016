package org.usfirst.frc.team1124.robot.commands.steptest;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command generates step test data from the drive train. It then analyzes the left and right collections of
 * step test data.
 * 
 * @author Mrs. Weston
 */
public class GeneratePIDDriveTrain extends CommandGroup {
	private CreatePIDFromStepTest leftPidFromStep;
	private CreatePIDFromStepTest rightPidFromStep;

	public GeneratePIDDriveTrain() {
		StepTestDriveTrain driveTrainStepTest = new StepTestDriveTrain();
		this.addSequential(driveTrainStepTest);

		leftPidFromStep = new CreatePIDFromStepTest(driveTrainStepTest.getLeftData());
		this.addSequential(leftPidFromStep);

		rightPidFromStep = new CreatePIDFromStepTest(driveTrainStepTest.getRightData());
		this.addSequential(rightPidFromStep);
	}

	public Double getLeftP() {
		return leftPidFromStep.getP();
	}

	public Double getLeftI() {
		return leftPidFromStep.getI();
	}

	public Double getLeftD() {
		return leftPidFromStep.getD();
	}

	public Double getRightP() {
		return rightPidFromStep.getP();
	}

	public Double getRightI() {
		return rightPidFromStep.getI();
	}

	public Double getRightD() {
		return rightPidFromStep.getD();
	}
}
