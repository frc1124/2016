package org.usfirst.frc.team1124.robot;

import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;

/**
 * The ports and other configurations for the robot.
 */
public class RobotMap {
	// CAN
	public static final int SHOOTER = 1;
	public static final int RIGHT_DRIVE_1 = 3;
	public static final int RIGHT_DRIVE_2 = 4;
	public static final int ARM_ACTUATOR = 5;
	public static final int LEFT_DRIVE_1 = 6;
	public static final int LEFT_DRIVE_2 = 7;
	public static final int ARM_WHEELS = 9;
	public static final int RAMP_BELTS = 10;
	
	// Digital
	public static final int RIGHT_DRIVE_A = 0;
	public static final int RIGHT_DRIVE_B = 1;
	public static final int SHOOTER_CAGE_LIGHT_SENSOR = 2;
	public static final int ARM_LIGHT_SENSOR_FORWARD = 3;
	public static final int ARM_LIMIT_SWITCH_BACK = 4;
	public static final int ARM_ENCODER_A = 6;
	public static final int ARM_ENCODER_B = 7;
	public static final int RAMP_LIGHT_SENSOR = 8;
	
	// Solenoids
	public static final int ARM_PISTON_A = 0;
	public static final int ARM_PISTON_B = 1;
	
	// Analog Input
	public static final int GYRO = 0;

	// Robot and Field Dimensions
	public static final double TARGET_HEIGHT_ON_WALL = 90.0;
	public static final double TARGET_HEIGHT = 14.0;
	
	public static final double CAMERA_MOUNT_ANGLE = 28.0;
	
	// Axis Camera Configuration
	public static final String CAMERA_IP = "10.11.24.11";
	
	public static final int CAMERA_COLOR_LEVEL = 100;
	public static final int CAMERA_BRIGHTNESS = 50;
	public static final int CAMERA_COMPRESSION = 30;
	public static final int CAMERA_MAX_FPS = 20;
	
	public static final Resolution CAMERA_RESOLUTION = Resolution.k320x240;
	public static final ExposureControl CAMERA_EXPOSURE = ExposureControl.kHold;
	public static final WhiteBalance CAMERA_WB = WhiteBalance.kFixedFluorescent2;
	
	// USB Camera Configuration
	public static final String USB_CAMERA_ID = "cam0";
	
	public static final int USB_CAMERA_MAX_FPS = 20;
	
	// Targeting Configuration
	public static final double CAMERA_PIXEL_WIDTH = 320.0;
	public static final double CAMERA_PIXEL_HEIGHT = 240.0;
	
	public static final double CAMERA_FOV = 47.0;
	public static final double CAMERA_VERTICAL_FOV = CAMERA_FOV * CAMERA_PIXEL_HEIGHT / CAMERA_PIXEL_WIDTH;
	
	public static final double CAMERA_X_OFFSET = -5.0;
	public static final double CAMERA_Y_OFFSET = 0.0;
	public static final double CAMERA_Z_OFFSET = 42.5;
}
