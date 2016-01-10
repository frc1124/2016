package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.teleop.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public CANTalon left_1, left_2, left_3, right_1, right_2, right_3;
	private RobotDrive firstpair;
	private RobotDrive secondpair;
	private RobotDrive thirdpair;
	
	public DriveTrain(){
		super("DriveTrain");
		
		left_1 = new CANTalon(Robot.configIO.getIntVal("left1"));
		left_2 = new CANTalon(Robot.configIO.getIntVal("left2"));
		left_3 = new CANTalon(Robot.configIO.getIntVal("left3"));
		
		right_1 = new CANTalon(Robot.configIO.getIntVal("right1"));
		right_2 = new CANTalon(Robot.configIO.getIntVal("right2"));
		right_3 = new CANTalon(Robot.configIO.getIntVal("right3"));
		
		firstpair = new RobotDrive(left_1, right_1);
		secondpair = new RobotDrive(left_2, right_2);
		thirdpair = new RobotDrive(left_3, right_3);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	// tank drive method
	
	public void drive_tank(double left, double right){
		firstpair.setInvertedMotor(MotorType.kFrontLeft, true);
		secondpair.setInvertedMotor(MotorType.kFrontLeft, true);
		thirdpair.setInvertedMotor(MotorType.kFrontLeft, true);
	
		
		firstpair.tankDrive(left, right);
		secondpair.tankDrive(left, right);
		thirdpair.tankDrive(left, right);
	}
	
	// arcade drive methods
	
	public void drive(double move, double rotate) {
		firstpair.setInvertedMotor(MotorType.kFrontLeft, true);
		secondpair.setInvertedMotor(MotorType.kFrontLeft, true);
		thirdpair.setInvertedMotor(MotorType.kFrontLeft, true);
		
		firstpair.arcadeDrive(move, rotate);
		secondpair.arcadeDrive(move, rotate);
		thirdpair.arcadeDrive(move, rotate);
	}
	
	public void drive(Joystick js){
		firstpair.setInvertedMotor(MotorType.kFrontLeft, true);
		secondpair.setInvertedMotor(MotorType.kFrontLeft, true);
		thirdpair.setInvertedMotor(MotorType.kFrontLeft, true);
		
		firstpair.arcadeDrive(js);
		secondpair.arcadeDrive(js);
		thirdpair.arcadeDrive(js);
	}
	
	
	// mode-independent methods
	
	public void stop(){
		firstpair.arcadeDrive(0, 0);
		secondpair.arcadeDrive(0, 0);
		thirdpair.arcadeDrive(0, 0);
	}
	
}
