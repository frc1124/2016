package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.teleop.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public CANTalon left_1, left_2, left_3, right_1, right_2, right_3;
	
	private RobotDrive firstpair;
	private RobotDrive secondpair;
	private RobotDrive thirdpair;
	
	private Encoder left;
	private Encoder right;
	
	public DriveTrain(){
		super("DriveTrain");
		
		left_1 = new CANTalon(Robot.configIO.getIntVal("left_1"));
		left_2 = new CANTalon(Robot.configIO.getIntVal("left_2"));
		left_3 = new CANTalon(Robot.configIO.getIntVal("left_3"));
		
		right_1 = new CANTalon(Robot.configIO.getIntVal("right_1"));
		right_2 = new CANTalon(Robot.configIO.getIntVal("right_2"));
		right_3 = new CANTalon(Robot.configIO.getIntVal("right_3"));
		
		firstpair = new RobotDrive(left_1, right_1);
		secondpair = new RobotDrive(left_2, right_2);
		thirdpair = new RobotDrive(left_3, right_3);
		
		int left_a_channel = Robot.configIO.getIntVal("left_enc_a");
		int left_b_channel = Robot.configIO.getIntVal("left_enc_b");
		
		int right_a_channel = Robot.configIO.getIntVal("right_enc_a");
		int right_b_channel = Robot.configIO.getIntVal("right_enc_b");
		
		
		firstpair.setInvertedMotor(MotorType.kRearLeft, true);
		secondpair.setInvertedMotor(MotorType.kRearLeft, true);
		thirdpair.setInvertedMotor(MotorType.kRearLeft, true);
		
		
		left = new Encoder(left_a_channel, left_b_channel, false, EncodingType.k4X);
		right = new Encoder(right_a_channel, right_b_channel, false, EncodingType.k4X);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	// encoders
	
	public double getLeftEncoderDistance(){
		return left.getDistance();
	}
	
	public boolean getLeftEncoderDirection(){
		return left.getDirection();
	}
	
	public double getLeftEncoderRate(){
		return left.getRate();
	}
	
	public double getRightEncoderDistance(){
		return right.getDistance();
	}
	
	public boolean getRightEncoderDirection(){
		return right.getDirection();
	}
	
	public double getRightEncoderRate(){
		return right.getRate();
	}
	
	// tank drive method
	
	public void drive_tank(double left, double right){
		firstpair.tankDrive(left, right);
		secondpair.tankDrive(left, right);
		thirdpair.tankDrive(left, right);
	}
	
	// arcade drive methods
	
	public void drive(double move, double rotate) {
		firstpair.arcadeDrive(move, rotate);
		secondpair.arcadeDrive(move, rotate);
		thirdpair.arcadeDrive(move, rotate);
	}
	
	public void drive(Joystick js){
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
