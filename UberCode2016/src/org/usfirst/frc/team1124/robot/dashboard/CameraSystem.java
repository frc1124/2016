package org.usfirst.frc.team1124.robot.dashboard;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.enums.CameraSelect;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class CameraSystem {
	private Image intake_frame;
	private Image shooter_frame;
	
	private CameraSelect current_camera;
	
	public void initShooterCamera(){
		try{
	        shooter_frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	        Robot.shooter_camera = new AxisCamera("10.11.24.78");
	        
	        Robot.shooter_camera.writeColorLevel(50);
	        Robot.shooter_camera.writeBrightness(50);
	        
	        Robot.shooter_camera.writeCompression(30);
	        
	        Robot.shooter_camera.writeResolution(Resolution.k320x240);
	        Robot.shooter_camera.writeExposureControl(ExposureControl.kHold);
	        
	        Robot.shooter_camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent1);
	        Robot.shooter_camera.writeMaxFPS(20);
		}catch(Exception e) {}
	}
	
	public void initIntakeCamera(){
		try{
	        intake_frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
			
			Robot.intake_camera = new USBCamera("cam0");
			Robot.intake_camera.openCamera();
		}catch(Exception e) {}
	}
	
	public void selectCamera(CameraSelect selection){
		current_camera = selection;
	}
	
	/** 
	 * Only run this method while the robot is ENABLED! This method gets the image and sends it to the dashboard for display and processing. 
	 */
	public void getImage(){
        try{
        	switch(current_camera){
        		case Intake:
                	Robot.intake_camera.getImage(intake_frame);
        			CameraServer.getInstance().setImage(intake_frame);
        		break;
        		case Shooter:
                	Robot.shooter_camera.getImage(shooter_frame);
                    CameraServer.getInstance().setImage(shooter_frame);
        		break;
        	}
        }catch(Exception e){}
	}
	
	/**
	 * Calculates the speed needed to shoot a high goal using the current image data
	 * TODO fill out this function with christian's code
	 * @return the rate for the shooter motor
	 */
	public double getRateForShooterToScore(){
		return 0.0;
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
}
