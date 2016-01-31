package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.drive.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Drive Train subsystem. Contains both the left and right gearboxes. </br>
 * The safeties are contained in LeftDrivePID.java and RightDrivePID.java (commands).
 */
public class DriveTrain extends Subsystem {
	
	public CANTalon left_1, left_2, left_3, right_1, right_2, right_3;
	
	public final double SETPOINT_TOLERANCE = 0;
	
	private final double ENCODER_DIST_PER_PULSE = Math.PI / 120;
	
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
		
		left = new Encoder(left_a_channel, left_b_channel, true, EncodingType.k4X);
		right = new Encoder(right_a_channel, right_b_channel, false, EncodingType.k4X);
		
		left.setDistancePerPulse(ENCODER_DIST_PER_PULSE);
		right.setDistancePerPulse(ENCODER_DIST_PER_PULSE);
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
		resetLeftEncoder();
		resetRightEncoder();
	}
	
	public void resetLeftEncoder(){
		left.reset();
	}
	
	public void resetRightEncoder(){
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
		drive(-js.getY(), -js.getX());
	}
	
	// mode-independent methods
	
	public void stop(){
		firstpair.arcadeDrive(0, 0);
		secondpair.arcadeDrive(0, 0);
		thirdpair.arcadeDrive(0, 0);
	}
	
	// Toggle Break/Coast
	
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
	
	// Manual Control
	public TalonControlMode getControlMode() {
		return left_1.getControlMode();
	}
	
	public void setControlMode(int mode) {
		left_1.setControlMode(mode);
		left_2.setControlMode(mode);
		left_3.setControlMode(mode);
		right_1.setControlMode(mode);
		right_2.setControlMode(mode);
		right_3.setControlMode(mode);
	}
	
	public void setLeftMotor(double speed) {
		this.left_1.set(speed);
		this.left_2.set(speed);
		this.left_3.set(speed);
	}

	public void drive_tank_auto(double left, double right) {
		firstpair.tankDrive(left, right,false);
		secondpair.tankDrive(left, right,false);
		thirdpair.tankDrive(left, right,false);
	}

	public double getLeftMotor() {
		return (this.left_1.getOutputVoltage() + this.left_2.getOutputVoltage() + this.left_3.getOutputVoltage()) / 3;
	}

	public void setRightMotor(double speed) {
		this.right_1.set(speed);
		this.right_2.set(speed);
		this.right_3.set(speed);
	}

	public double getRightMotor() {
		return (this.right_1.getOutputVoltage() + this.right_2.getOutputVoltage() + this.right_3.getOutputVoltage()) / 3;
	}
}