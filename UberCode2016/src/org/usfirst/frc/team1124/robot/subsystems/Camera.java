package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.RobotMap;
import org.usfirst.frc.team1124.robot.tools.vision.VisionTools;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * The subsystem that manages camera streams and data processing
 */
public class Camera extends Subsystem {
	private Image shooter_frame;
	
	// if camera is being held by auto code
	private boolean camera_held = false;

	public Camera(){
		super("camera");
		
		initShooterCamera();
		initIntakeCamera();
	}
	
	protected void initDefaultCommand() {}
	
	private void initShooterCamera(){
		try{
	        shooter_frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	        Robot.shooter_camera = new AxisCamera(RobotMap.CAMERA_IP);
	        
	        Robot.shooter_camera.writeColorLevel(RobotMap.CAMERA_COLOR_LEVEL);
	        Robot.shooter_camera.writeBrightness(RobotMap.CAMERA_BRIGHTNESS);
	        
	        Robot.shooter_camera.writeCompression(RobotMap.CAMERA_COMPRESSION);
	        
	        Robot.shooter_camera.writeResolution(RobotMap.CAMERA_RESOLUTION);
	        Robot.shooter_camera.writeExposureControl(RobotMap.CAMERA_EXPOSURE);
	        
	        Robot.shooter_camera.writeWhiteBalance(RobotMap.CAMERA_WB);
	        Robot.shooter_camera.writeMaxFPS(RobotMap.CAMERA_MAX_FPS);
		}catch(Exception e) {
			System.out.println("Failed to initialize Axis Camera.");
		}
	}
	
	private void initIntakeCamera(){
		try{
			Robot.intake_camera = new USBCamera(RobotMap.USB_CAMERA_ID);
			Robot.intake_camera.openCamera();
			
			Robot.intake_camera.setSize(160, 120);
			
			Robot.intake_camera.setFPS(RobotMap.USB_CAMERA_MAX_FPS);
			
			CameraServer.getInstance().startAutomaticCapture(Robot.intake_camera);
		}catch(Exception e) {
			System.out.println("Failed to initialize Microsoft LifeCam.");
		}
	}
	
	public void setHeld(boolean held){
		camera_held = held;
	}
	
	public boolean isHeld(){
		return camera_held;
	}
	
	/** 
	 * Polls the target dimensions from the dashboard.
	 * If they are not present, it returns {-1, -1}.
	 * 
	 * @return An array of { width, height }
	 * */
	public double[] getTargetDimensions(){
		double width = -1;
		double height = -1;
		
		try{
			width = SmartDashboard.getNumber("vision_target_width");
			height = SmartDashboard.getNumber("vision_target_height");
		}catch(Exception e){}
		
		double data[] = {width, height};
		
		return data;
	}
	
	/** 
	 * Polls the target dimensions from the dashboard.
	 * If they are not present, it returns {-1, -1}.
	 * 
	 * @return an array of the {x center of mass, y center of mass}
	 * */
	public double[] getTargetCenterOfMass(){
		double x = -1;
		double y = -1;
		
		try{
			x = SmartDashboard.getNumber("vision_target_x_cm");
			y = SmartDashboard.getNumber("vision_target_y_cm");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		double data[] = {x, y};
		
		return data;
	}
}
