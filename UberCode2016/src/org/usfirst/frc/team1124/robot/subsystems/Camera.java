package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.tools.VisionTools;

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
	        Robot.shooter_camera = new AxisCamera("10.11.24.11");
	        
	        Robot.shooter_camera.writeColorLevel(50);
	        Robot.shooter_camera.writeBrightness(50);
	        
	        Robot.shooter_camera.writeCompression(30);
	        
	        Robot.shooter_camera.writeResolution(Resolution.k320x240);
	        Robot.shooter_camera.writeExposureControl(ExposureControl.kHold);
	        
	        Robot.shooter_camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent1);
	        Robot.shooter_camera.writeMaxFPS(20);
		}catch(Exception e) {}
	}
	
	private void initIntakeCamera(){
		try{
			Robot.intake_camera = new USBCamera("cam1");
			Robot.intake_camera.openCamera();
			
			Robot.intake_camera.setSize(160, 120);
			
			Robot.intake_camera.setFPS(15);
			
			CameraServer.getInstance().startAutomaticCapture(Robot.intake_camera);
		}catch(Exception e) {}
	}
	
	public void setHeld(boolean x){
		camera_held = x;
	}
	
	public boolean isHeld(){
		return camera_held;
	}
	
	/** 
	 * Only run this method while the robot is ENABLED! This method gets the image and sends it to the dashboard for display and processing. 
	 */
	public void getImage(){
        try{
        	Robot.shooter_camera.getImage(shooter_frame);
            CameraServer.getInstance().setImage(shooter_frame);
        }catch(Exception e){}
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
		}catch(Exception e){}
		
		double data[] = {x, y};
		
		return data;
	}
	
	/**
	 * Get the average distance to the target.
	 * @return The average distance (left edge distance and right edge distance averaged)
	 */
	public double getTargetAvgDistance(){
		double result = -1.0;
		
		/*
		 * What each point means on the image:
		 * 
		 *   XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		 *   X point_1           point_2 X
		 *   X                           X
		 *   X                           X
		 *   X                           X
		 *   X                           X
		 *   X                           X
		 *   X                           X
		 *   X                           X
		 *   X point_4           point_3 X
		 *   XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		 */
		
		try{
			boolean top_left = SmartDashboard.getBoolean("vision_top_left");
			boolean bottom_right = SmartDashboard.getBoolean("vision_bottom_right");
			
			double height = SmartDashboard.getNumber("vision_target_height");
			
			double TLY = SmartDashboard.getNumber("vision_target_p1_y");
			double BLY = 0;//SmartDashboard.getNumber("vision_target_p4_y");
			double TRY = 0;//SmartDashboard.getNumber("vision_target_p2_y");
			double BRY = 0;//SmartDashboard.getNumber("vision_target_p3_y");
			
//			double[] distances = {0,0,0,0};
//			VisionTools.goalDistances(TLY, BLY, TRY, BRY, distances);
			
//			result = (distances[0] + distances[1] + distances[2] + distances[3]) / 4;
			
			double[] distances = {-1,-1};
			VisionTools.goalDistances(top_left, bottom_right, TLY, height, distances);
			
			result = (distances[0] + distances[1]) / 2;
		}catch(Exception e){}
		
		return result;
	}
	
	private static final double shooterAngle = 40 * Math.PI / 180;
	private static final double coefDrag = .054;
	public double getCalculatedShooterRPM(double distanceToGoalFeet){
		double distanceToGoalMeters = distanceToGoalFeet * 3.2808;
		return -9.8 * distanceToGoalMeters * distanceToGoalMeters /
				(2 * 1.594 * Math.cos(shooterAngle) - 2 * distanceToGoalMeters * Math.sin(shooterAngle) +
				distanceToGoalMeters * distanceToGoalMeters * .054 * 1.225 * Math.tan(shooterAngle) / 2 / Math.PI / .127 / .3);
		
		
		return 0.0; // TODO code this
	}
}
