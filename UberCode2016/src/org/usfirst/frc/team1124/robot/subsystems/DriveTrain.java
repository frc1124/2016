package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.commands.teleop.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public CANTalon front_left_motor, back_left_motor,  front_right_motor, back_right_motor;
	private RobotDrive drive;
	
	public DriveTrain(){
		super("DriveTrain");
		
		front_left_motor = new CANTalon(RobotMap.drivetrain.flMotor);
		back_left_motor = new CANTalon(RobotMap.drivetrain.blMotor);
		
		front_right_motor = new CANTalon(RobotMap.drivetrain.frMotor);
		back_right_motor = new CANTalon(RobotMap.drivetrain.brMotor);
		
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	// tank drive method
	
	public void drive_tank(double left, double right){
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
		drive.tankDrive(left, right);
	}
	
	// arcade drive methods
	
	public void drive(double move, double rotate) {
		drive.setInvertedMotor(MotorType.kFrontLeft, false);
		drive.setInvertedMotor(MotorType.kRearLeft, false);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.arcadeDrive(move, rotate);
	}
	
	public void drive(Joystick js){
		drive.setInvertedMotor(MotorType.kFrontLeft, false);
		drive.setInvertedMotor(MotorType.kRearLeft, false);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.arcadeDrive(js);
	}
	
	// mecanum drive methods

	public void drive_mecanum(double x, double y, double r, double theta) {
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.mecanumDrive_Cartesian(x, y, r, theta);
	}

	public void drive_mecanum_basic(double x, double y) {
		double r = 0;
		double theta = 0;
		
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.mecanumDrive_Cartesian(x, y, r, theta);
	}
	
	public void drive_mecanum(Joystick js){
		double y = js.getY();
		double x = js.getX();
		double r = js.getZ();
		double theta = 0;
		
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.mecanumDrive_Cartesian(x, y, r, theta);
	}
	
	public void drive_mecanum_basic(Joystick js){
		double y = js.getY();
		double x = js.getX();
		double r = 0;
		double theta = 0;
		
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, false);
		drive.setInvertedMotor(MotorType.kRearRight, false);
		
		drive.mecanumDrive_Cartesian(x, y, r, theta);
	}

	// mode-independent methods
	
	public void stop(){
		drive.arcadeDrive(0, 0);
	}
	
}
