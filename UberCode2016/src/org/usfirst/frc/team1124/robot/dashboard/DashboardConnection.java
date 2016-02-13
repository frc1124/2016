package org.usfirst.frc.team1124.robot.dashboard;

import java.util.ArrayList;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.camera.SelectCamera;
import org.usfirst.frc.team1124.robot.enums.AutoDefensePosition;
import org.usfirst.frc.team1124.robot.enums.AutoDefenseType;
import org.usfirst.frc.team1124.robot.enums.AutoMode;
import org.usfirst.frc.team1124.robot.enums.CameraSelect;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardConnection {
	private static boolean firstCall = true;
	
	// use shooter since that is the default/first state
	private CameraSelect prevCameraSelection = CameraSelect.Shooter;
	
	/**
	 * Get data for autonomous
	 */
	public static class Auto {
		public static AutoMode getMode(){
			AutoMode mode = AutoMode.Nothing;
			
			try{
				int mode_id =  (int) SmartDashboard.getNumber("auto_mode");
				
				switch(mode_id){
					case 0:
						mode = AutoMode.Nothing;
					break;
					case 1:
						mode = AutoMode.GetToDefense;
					break;
					case 2:
						mode = AutoMode.CrossDefense;
					break;
					case 3:
						mode = AutoMode.ScoreHighGoal;
					break;
					case 4:
						mode = AutoMode.ScoreLowGoal;
					break;
				}
			}catch(Exception e){}
			
			return mode;
		}
		
		public static AutoDefenseType getDefenseType(){
			AutoDefenseType type = AutoDefenseType.SomethingElse;
			
			try{
				int type_id =  (int) SmartDashboard.getNumber("auto_defense_type");
				
				switch(type_id){
					case 0:
						type = AutoDefenseType.Moat;
					break;
					case 1:
						type = AutoDefenseType.Ramparts;
					break;
					case 2:
						type = AutoDefenseType.RockWall;
					break;
					case 3:
						type = AutoDefenseType.RoughTerrain;
					break;
					case 4:
						type = AutoDefenseType.SomethingElse;
					break;
				}
			}catch(Exception e){}
			
			return type;
		}
		
		public static AutoDefensePosition getDefensePosition(){
			AutoDefensePosition position = AutoDefensePosition.SpyBox;
			
			try{
				int position_id =  (int) SmartDashboard.getNumber("auto_defense_position");
				
				switch(position_id){
					case 0:
						position = AutoDefensePosition.Pos_1;
					break;
					case 1:
						position = AutoDefensePosition.Pos_2;
					break;
					case 2:
						position = AutoDefensePosition.Pos_3;
					break;
					case 3:
						position = AutoDefensePosition.Pos_4;
					break;
					case 4:
						position = AutoDefensePosition.Pos_5;
					break;
					case 5:
						position = AutoDefensePosition.SpyBox;
					break;
				}
			}catch(Exception e){}
			
			return position;
		}
		
	}
	
	// dashboard data syncing
	
	public void updateDashboard(){
		// one-time operations
		oneTimeOperations();
		
		// camera data
		updateCameraInfo();
		
		// roboRIO
		updateRoboRIO();
		
		// power distribution panel
		updatePDP();
		
		// encoders
		updateSensors();
		
		// config
		sendConfigToDash();
	}
	
	private void oneTimeOperations(){
		if(firstCall){
			// general data
			SmartDashboard.putString("code_revision", Robot.codeRevision);
			
			// PDP port "map"
	    	/** TODO update these with final robot ports */
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
	
	private void updateCameraInfo(){
		try{
			boolean selection = SmartDashboard.getBoolean("camera_selector");
			CameraSelect camera_selection = selection ? CameraSelect.Shooter : CameraSelect.Intake;
			
			if(prevCameraSelection != camera_selection){
				SelectCamera command = new SelectCamera(camera_selection);
				
				Scheduler.getInstance().add(command);
			}
			
			prevCameraSelection = camera_selection;
		}catch(Exception e){}
		
		SmartDashboard.putBoolean("camera_override", Robot.camera.isHeld());
		
		CameraSelect select = Robot.camera.getActiveCamera();
		
		boolean result = (select == CameraSelect.Shooter);
		
		SmartDashboard.putBoolean("camera_enabled", result);
	}
	
	private void updateSensors(){
		// drive encoders
		SmartDashboard.putNumber("left_drive_encoder_dist", Robot.drivetrain.getLeftEncoderDistance());
		SmartDashboard.putNumber("left_drive_encoder_rate", Robot.drivetrain.getLeftEncoderRate());
		
		SmartDashboard.putNumber("right_drive_encoder_dist", Robot.drivetrain.getRightEncoderDistance());
		SmartDashboard.putNumber("right_drive_encoder_rate", Robot.drivetrain.getRightEncoderRate());
		
		// shooter encoder
		SmartDashboard.putNumber("shooter_encoder_rate", Robot.shooter_pid.getRate());

		// arm potentiometer
		SmartDashboard.putNumber("arm_actuator_encoder_dist", Robot.arm_actuator_pid.getDistance());
		SmartDashboard.putNumber("arm_actuator_encoder_rate", Robot.arm_actuator_pid.getRate());
		
		// arm limit switches
		boolean[] switches = Robot.arm_actuator_pid.getLimitSwitchStates();
		
		SmartDashboard.putBoolean("arm_actuator_back_left", switches[0]);
		SmartDashboard.putBoolean("arm_actuator_back_right", switches[1]);
		SmartDashboard.putBoolean("arm_actuator_forward_left", switches[2]);
		SmartDashboard.putBoolean("arm_actuator_forward_right", switches[3]);
		
		// ball detection sensor
		SmartDashboard.putBoolean("ramp_belts_sensor", Robot.ramp_belts.getBallDetected());
		
		// drive encoder resets
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
