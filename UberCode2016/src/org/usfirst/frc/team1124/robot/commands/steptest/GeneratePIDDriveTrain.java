package org.usfirst.frc.team1124.robot.commands.steptest;

import org.usfirst.frc.team1124.robot.tools.StepTest;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		StepTest leftDrive = new StepTest();
		StepTest rightDrive = new StepTest();
		StepTestDriveTrain driveTrainStepTest = new StepTestDriveTrain(leftDrive,rightDrive);
		this.addSequential(driveTrainStepTest);

		leftPidFromStep = new CreatePIDFromStepTest(leftDrive);
		this.addSequential(leftPidFromStep);

		rightPidFromStep = new CreatePIDFromStepTest(rightDrive);
		this.addSequential(rightPidFromStep);
	}

	@Override
	protected void end() {
		SmartDashboard.putNumber("left_p",this.getLeftP());
		SmartDashboard.putNumber("left_i",this.getLeftI());
		SmartDashboard.putNumber("left_d",this.getLeftD());
		SmartDashboard.putNumber("right_p",this.getRightP());
		SmartDashboard.putNumber("right_i",this.getRightI());
		SmartDashboard.putNumber("right_d",this.getRightD());
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
