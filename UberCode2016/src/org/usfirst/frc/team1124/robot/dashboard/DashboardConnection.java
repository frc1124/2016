package org.usfirst.frc.team1124.robot.dashboard;

import edu.wpi.first.wpilibj.CameraServer;

import java.util.ArrayList;

import org.usfirst.frc.team1124.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.ExposureControl;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;
import edu.wpi.first.wpilibj.vision.AxisCamera.WhiteBalance;
//import edu.wpi.first.wpilibj.vision.USBCamera;

public class DashboardConnection {
	private static boolean firstCall = true;
    
	Image frame;
    AxisCamera camera;
	
	//private CameraServer srv;
	//private USBCamera camera;
	
	public void initCamera(){
		//srv = CameraServer.getInstance();
	    
		try{
	        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	        camera = new AxisCamera("10.11.24.78");
	        
	        camera.writeColorLevel(50);
	        camera.writeBrightness(50);
	        
	        camera.writeCompression(30);
	        
	        camera.writeResolution(Resolution.k320x240);
	        camera.writeExposureControl(ExposureControl.kHold);
	        
	        camera.writeWhiteBalance(WhiteBalance.kFixedFluorescent1);
	        camera.writeMaxFPS(20);
		}catch(Exception e) {}
		
		//camera = new USBCamera("cam0");
        //camera.openCamera();
		
        //camera.setWhiteBalanceManual(4500);
        //camera.setExposureManual(0);
		//camera.setBrightness(100);
        
        //srv.setSize(1); // 320x240
        
        //return camera;
	}
	
	/** Only run this method while the robot is ENABLED! This method gets the image and sends it to the dashboard for display and processing. */
	public void getImage(){
        try{
        	camera.getImage(frame);
            CameraServer.getInstance().setImage(frame);
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
	
	/*
	public void configCamera(){
        camera.setWhiteBalanceManual(4500);
        camera.setExposureManual(0);
		camera.setBrightness(100);
	}
	*/
	
	public void updateDashboard(){
		// one-time operations
		oneTimeOperations();
		
		// roboRIO
		updateRoboRIO();
		
		// power distribution panel
		updatePDP();
		
		// encoders
		updateEncoders();
		
		// config
		sendConfigToDash();
	}
	
	private void oneTimeOperations(){
		if(firstCall){
			// general data
			SmartDashboard.putString("code_revision", Robot.codeRevision);
			
			// PDP port "map"
			SmartDashboard.putString("pdp_can_key_port_0", "Right Drive [Rear] (CAN ID#6)");
			SmartDashboard.putString("pdp_can_key_port_1", "Right Drive [Top] (CAN ID#5)");
			SmartDashboard.putString("pdp_can_key_port_2", "null");
			SmartDashboard.putString("pdp_can_key_port_3", "null");
			SmartDashboard.putString("pdp_can_key_port_4", "null");
			SmartDashboard.putString("pdp_can_key_port_5", "null");
			SmartDashboard.putString("pdp_can_key_port_6", "null");
			SmartDashboard.putString("pdp_can_key_port_7", "null");
			SmartDashboard.putString("pdp_can_key_port_8", "null");
			SmartDashboard.putString("pdp_can_key_port_9", "null");
			SmartDashboard.putString("pdp_can_key_port_10", "null");
			SmartDashboard.putString("pdp_can_key_port_11", "null");
			SmartDashboard.putString("pdp_can_key_port_12", "Right Drive [Front] (CAN ID#4)");
			SmartDashboard.putString("pdp_can_key_port_13", "Left Drive [Top] (CAN ID#2)");
			SmartDashboard.putString("pdp_can_key_port_14", "Left Drive [Front] (CAN ID#1)");
			SmartDashboard.putString("pdp_can_key_port_15", "Left Drive [Rear] (CAN ID#3)");
			
			firstCall = false;
		}
	}
	
	private void updateEncoders(){
		// drive encoders
		SmartDashboard.putNumber("left_drive_encoder_dist", Robot.drivetrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("left_drive_encoder_rate", Robot.drivetrain.getLeftEncoderRate());
		
		SmartDashboard.putNumber("right_drive_encoder_dist", Robot.drivetrain.getRightEncoderDistance());
		SmartDashboard.putNumber("right_drive_encoder_rate", Robot.drivetrain.getRightEncoderRate());
		
		try {
			boolean reset_right = SmartDashboard.getBoolean("right_encoder_reset");
			boolean reset_left = SmartDashboard.getBoolean("left_encoder_reset");
			
			if(reset_right){
				Robot.drivetrain.resetRightEncoder();
			}
			
			if(reset_left){
				Robot.drivetrain.resetLeftEncoder();
			}
		} catch(Exception e) {}
		
		SmartDashboard.putNumber("left_drive_speed", Robot.drivetrain.left_1.get());
		SmartDashboard.putNumber("right_drive_speed", Robot.drivetrain.right_1.get());
	}
	
	private void updateRoboRIO(){
		SmartDashboard.putNumber("rio_input_voltage", ControllerPower.getInputVoltage());
		SmartDashboard.putNumber("rio_input_current", ControllerPower.getInputCurrent());
		
		SmartDashboard.putNumber("rio_voltage_3.3v", ControllerPower.getVoltage3V3());
		SmartDashboard.putNumber("rio_current_3.3v", ControllerPower.getCurrent3V3());
		SmartDashboard.putBoolean("rio_enabled_3.3v", ControllerPower.getEnabled3V3());
		SmartDashboard.putNumber("rio_fault_count_3.3v", ControllerPower.getFaultCount3V3());
		
		SmartDashboard.putNumber("rio_voltage_5v", ControllerPower.getVoltage5V());
		SmartDashboard.putNumber("rio_current_5v", ControllerPower.getCurrent5V());
		SmartDashboard.putBoolean("rio_enabled_5v", ControllerPower.getEnabled5V());
		SmartDashboard.putNumber("rio_fault_count_5v", ControllerPower.getFaultCount5V());
		
		SmartDashboard.putNumber("rio_voltage_6v", ControllerPower.getVoltage6V());
		SmartDashboard.putNumber("rio_current_6v", ControllerPower.getCurrent6V());
		SmartDashboard.putBoolean("rio_enabled_6v", ControllerPower.getEnabled6V());
		SmartDashboard.putNumber("rio_fault_count_6v", ControllerPower.getFaultCount6V());
	}
	
	private void updatePDP(){
		SmartDashboard.putNumber("pdp_voltage", Robot.pdp.getVoltage());
		SmartDashboard.putNumber("pdp_temp", (Robot.pdp.getTemperature() * (9.00/5.00)) + 32.00);
		SmartDashboard.putNumber("pdp_total_current", Robot.pdp.getTotalCurrent());
		SmartDashboard.putNumber("pdp_total_power", Robot.pdp.getTotalPower());
		SmartDashboard.putNumber("pdp_total_energy", Robot.pdp.getTotalEnergy());
		
		for(int i = 0; i < 16; i++){
			SmartDashboard.putNumber("pdp_port_" + i + "_current", Robot.pdp.getCurrent(i));
		}
		
		try{
			boolean resetTotalEnergy = SmartDashboard.getBoolean("reset_pdp_total_energy");
			boolean clearStickyFaults = SmartDashboard.getBoolean("clear_pdp_sticky_faults");
			
			if(resetTotalEnergy){
				Robot.pdp.resetTotalEnergy();
			}
			
			if(clearStickyFaults){
				Robot.pdp.clearStickyFaults();
			}
		}catch(Exception e){}
	}
	
	private void sendConfigToDash(){
		ArrayList<String> temp = Robot.configIO.getConfigText();
		
		try{
			boolean updateConfig = SmartDashboard.getBoolean("update_config");
			
			if(updateConfig){
				for(int i = 0; i < temp.size(); i++){
					String newData = SmartDashboard.getString("new_config_" + i);
					
					String[] items = newData.split(" ");
					
					Robot.configIO.changeKeyVal(items[0], items[1]);
				}
				
				SmartDashboard.putBoolean("update_config", false);
			}
		}catch(Exception e) {}
		
		ArrayList<String> list = Robot.configIO.getConfigText();
		
		SmartDashboard.putNumber("config_count", list.size());
		
		for(int i = 0; i < list.size(); i++){
			SmartDashboard.putString("config_" + i, list.get(i));
		}
	}
}
