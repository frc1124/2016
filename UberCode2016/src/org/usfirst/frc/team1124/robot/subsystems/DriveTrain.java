package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.drive.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	private CANTalon left_1, left_2, left_3, right_1, right_2, right_3;
	
	// PID constants used for auto targeting and hold position
	public final double P = 1;
	public final double I = 0.01;
	public final double D = 0;
	
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
		
		left = new Encoder(left_a_channel, left_b_channel, false, EncodingType.k4X);
		right = new Encoder(right_a_channel, right_b_channel, true, EncodingType.k4X);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	// encoder methods
	
	public double getLeftEncoderDistance(){
		return left.getDistance();
	}
	
	public boolean getLeftEncoderDirection(){
		return left.getDirection();
	}
	
	public double getLeftEncoderRate(){
		return left.getRate();
	}
	
	public boolean getLeftEncoderStopped(){
		return left.getStopped();
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
	
	public boolean getRightEncoderStopped(){
		return right.getStopped();
	}
	
	public void resetEncoders(){
		left.reset();
		right.reset();
	}
	
	// tank drive method
	
	public void drive_tank(double left, double right){
		firstpair.tankDrive(left, right);
		secondpair.tankDrive(left, right);
		thirdpair.tankDrive(left, right);
	}
	
	public void drive_tank(Joystick js){
		drive_tank(js.getY(), js.getThrottle());
	}
	
	// arcade drive methods
	
	private void drive(double move, double rotate) {
		firstpair.arcadeDrive(move, rotate);
		secondpair.arcadeDrive(move, rotate);
		thirdpair.arcadeDrive(move, rotate);
	}
	
	public void drive(Joystick js){
		drive(-js.getY(), js.getX());
	}
	
	// mode-independent methods
	
	public void stop(){
		firstpair.arcadeDrive(0, 0);
		secondpair.arcadeDrive(0, 0);
		thirdpair.arcadeDrive(0, 0);
	}
	
	// toggle break/coast
	
	public void setCoast(){
		left_1.enableBrakeMode(false);
		left_2.enableBrakeMode(false);
		left_3.enableBrakeMode(false);
		right_1.enableBrakeMode(false);
		right_2.enableBrakeMode(false);
		right_3.enableBrakeMode(false);
	}
	
	public void setBrake(){
		left_1.enableBrakeMode(true);
		left_2.enableBrakeMode(true);
		left_3.enableBrakeMode(true);
		right_1.enableBrakeMode(true);
		right_2.enableBrakeMode(true);
		right_3.enableBrakeMode(true);
	}
}