package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.commands.drive.ArcadeDriveJoystick;
import org.usfirst.frc.team1124.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Drive Train subsystem. Contains both the left and right gear boxes. </br>
 * The safeties are contained in LeftDrivePID.java and RightDrivePID.java (commands).
 */
public class DriveTrain extends Subsystem {
	public CANTalon left_1, left_2, right_1, right_2;
	
	public final double SETPOINT_TOLERANCE = Math.PI / 60;
	
	private final double ENCODER_DIST_PER_PULSE = Math.PI / 120;
	
	private RobotDrive firstpair;
	private RobotDrive secondpair;
	
	private Encoder left;
	private Encoder right;
	
	private AnalogGyro gyro;
	private BuiltInAccelerometer accel;
	
	public DriveTrain(){
		super("DriveTrain");
		
		left_1 = new CANTalon(RobotMap.LEFT_DRIVE_1);
		left_2 = new CANTalon(RobotMap.LEFT_DRIVE_2);
		
		right_1 = new CANTalon(RobotMap.RIGHT_DRIVE_1);
		right_2 = new CANTalon(RobotMap.RIGHT_DRIVE_2);
		
		firstpair = new RobotDrive(left_1, right_1);
		secondpair = new RobotDrive(left_2, right_2);
		
		// int left_a_channel = 2;
		// int left_b_channel = 3;
		
		int right_a_channel = RobotMap.RIGHT_DRIVE_A;
		int right_b_channel = RobotMap.RIGHT_DRIVE_B;
		
		// left = new Encoder(left_a_channel, left_b_channel, true, EncodingType.k4X);
		right = new Encoder(right_a_channel, right_b_channel, false, EncodingType.k4X);
		
		// left.setDistancePerPulse(ENCODER_DIST_PER_PULSE);
		right.setDistancePerPulse(ENCODER_DIST_PER_PULSE);
		
		gyro = new AnalogGyro(RobotMap.GYRO);
		gyro.initGyro();
		
		accel = new BuiltInAccelerometer();
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	// gyro methods
	
	public void resetGyro(){
		gyro.reset();
	}
	
	/**
	 * Goes beyond 360 degrees
	 * @return absolute full angle that is beyond 360 degrees after 1 rotation
	 */
	public double getFullAngle(){
		return gyro.getAngle();
	}
	
	/**
	 * Gets an angle between 0 and 360
	 */
	public double getAngle(){
		return gyro.getAngle() / 360.0;
	}
	
	public double getAngularRate(){
		return gyro.getRate();
	}
	
	// accelerometer methods
	
	public double getAccelX(){
		return accel.getX();
	}
	
	public double getAccelY(){
		return -1 * ((accel.getY() * Math.cos(60.0 * (Math.PI/180.0))) - (accel.getZ() * Math.sin(60.0 * (Math.PI/180.0))));
	}
	
	public double getAccelZ(){
		return accel.getZ() * Math.cos(60.0 * (Math.PI/180.0)) + accel.getY() * Math.sin(60.0 * (Math.PI/180.0));
	}
	
	// encoder methods
	
	/** @deprecated */
	public double getLeftEncoderDistance(){
		return 0; //left.getDistance();
	}
	
	/** @deprecated */
	public boolean getLeftEncoderDirection(){
		return false; //left.getDirection();
	}
	
	/** @deprecated */
	public double getLeftEncoderRate(){
		return 0; //left.getRate();
	}
	
	/** @deprecated */
	public boolean getLeftEncoderStopped(){
		return false; //left.getStopped();
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
	}
	
	public void drive_tank(Joystick js){
		drive_tank(js.getY(), js.getThrottle());
	}
	
	// arcade drive methods
	
	private void drive(double move, double rotate) {
		firstpair.arcadeDrive(move, rotate);
		secondpair.arcadeDrive(move, rotate);
	}
	
	public void drive(Joystick js){
		drive(-js.getY(), -js.getX());
	}
	
	// mode-independent methods
	
	public void stop(){
		firstpair.arcadeDrive(0, 0);
		secondpair.arcadeDrive(0, 0);
	}
	
	// Toggle Break/Coast
	
	public void setBrakeMode(boolean enabled){
		left_1.enableBrakeMode(enabled);
		left_2.enableBrakeMode(enabled);
		right_1.enableBrakeMode(enabled);
		right_2.enableBrakeMode(enabled);	
	}
	
	// Manual Control
	public TalonControlMode getControlMode() {
		return left_1.getControlMode();
	}
	
	public void setControlMode(int mode) {
		left_1.setControlMode(mode);
		left_2.setControlMode(mode);
		right_1.setControlMode(mode);
		right_2.setControlMode(mode);
	}
	
	public void setLeftMotor(double speed) {
		this.left_1.set(speed);
		this.left_2.set(speed);
	}
	
	public void setRightMotor(double speed) {
		this.right_1.set(speed);
		this.right_2.set(speed);
	}

	public void drive_tank_auto(double left, double right) {
		firstpair.tankDrive(left, right, false);
		secondpair.tankDrive(left, right, false);
	}

	public double getLeftMotor() {
		return (this.left_1.getOutputVoltage() + this.left_2.getOutputVoltage()) / 2;
	}

	public double getRightMotor() {
		return (this.right_1.getOutputVoltage() + this.right_2.getOutputVoltage()) / 2;
	}
}
