package org.usfirst.frc.team1124.robot.dashboard;

import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class DashboardConnection {
	private static boolean firstCall = false;
	
	public void initCamera(){
		CameraServer srv = CameraServer.getInstance();
		
		USBCamera camera = new USBCamera("cam0");
        camera.openCamera();
        
        srv.setSize(0); // 640 by 480
        srv.startAutomaticCapture(camera);
	}
	
	public void updateDashboard(){
		// one-time operations
		if(firstCall){
			// general data
			SmartDashboard.putString("code_revision", Robot.codeRevision);
			
			// PDP port "map"
			SmartDashboard.putString("pdp_can_key_port_0", "Center Drive Motor");
			SmartDashboard.putString("pdp_can_key_port_1", "Container Arm Motor (CAN ID#5)");
			SmartDashboard.putString("pdp_can_key_port_2", "Lift Motor 1 (CAN ID#6)");
			SmartDashboard.putString("pdp_can_key_port_3", "Lift Motor 2 (CAN ID#7)");
			SmartDashboard.putString("pdp_can_key_port_4", "null");
			SmartDashboard.putString("pdp_can_key_port_5", "null");
			SmartDashboard.putString("pdp_can_key_port_6", "null");
			SmartDashboard.putString("pdp_can_key_port_7", "null");
			SmartDashboard.putString("pdp_can_key_port_8", "null");
			SmartDashboard.putString("pdp_can_key_port_9", "null");
			SmartDashboard.putString("pdp_can_key_port_10", "null");
			SmartDashboard.putString("pdp_can_key_port_11", "null");
			SmartDashboard.putString("pdp_can_key_port_12", "Front Left Drive Motor (CAN ID#2)");
			SmartDashboard.putString("pdp_can_key_port_13", "Front Right Drive Motor (CAN ID#1)");
			SmartDashboard.putString("pdp_can_key_port_14", "Back Left Drive Motor (CAN ID#3)");
			SmartDashboard.putString("pdp_can_key_port_15", "Back Right Drive Motor (CAN ID#4)");
			
			firstCall = false;
		}
		
		// roboRIO
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
		
		// power distribution panel
		SmartDashboard.putNumber("pdp_voltage", Robot.pdp.getVoltage());
		SmartDashboard.putNumber("pdp_temp", Robot.pdp.getTemperature());
		SmartDashboard.putNumber("pdp_total_current", Robot.pdp.getTotalCurrent());
		SmartDashboard.putNumber("pdp_total_power", Robot.pdp.getTotalPower());
		SmartDashboard.putNumber("pdp_total_energy", Robot.pdp.getTotalEnergy());
		
		for(int i = 0; i < 16; i++){
			SmartDashboard.putNumber("pdp_port_" + i + "_current", Robot.pdp.getCurrent(i));
		}
		
		boolean resetTotalEnergy = SmartDashboard.getBoolean("reset_pdp_total_energy");
		boolean clearStickyFaults = SmartDashboard.getBoolean("clear_pdp_sticky_faults");
		
		if(resetTotalEnergy){
			Robot.pdp.resetTotalEnergy();
		}
		
		if(clearStickyFaults){
			Robot.pdp.clearStickyFaults();
		}
		
		// pneumatics control module (if applicable)
		SmartDashboard.putBoolean("pressure_switch_state", Robot.compressor.getPressureSwitchValue());
		SmartDashboard.putBoolean("compressor_enabled", Robot.compressor.enabled());
		SmartDashboard.putNumber("compressor_current", Robot.compressor.getCompressorCurrent());
		
		SmartDashboard.putBoolean("compressor_close_loop_enabled", Robot.compressor.getClosedLoopControl());
		SmartDashboard.putBoolean("compressor_current_fault", Robot.compressor.getCompressorCurrentTooHighFault());
		SmartDashboard.putBoolean("compressor_current_sticky_fault", Robot.compressor.getCompressorCurrentTooHighStickyFault());
		SmartDashboard.putBoolean("compressor_shorted_fault", Robot.compressor.getCompressorShortedFault());
		SmartDashboard.putBoolean("compressor_shorted_sticky_fault", Robot.compressor.getCompressorShortedStickyFault());
		SmartDashboard.putBoolean("compressor_not_connected_fault", Robot.compressor.getCompressorNotConnectedFault());
		SmartDashboard.putBoolean("compressor_not_connected_sticky_fault", Robot.compressor.getCompressorNotConnectedStickyFault());
		
		boolean clearAllPCMStickyFaults = SmartDashboard.getBoolean("reset_pcm_faults");
		boolean enableCompressor = SmartDashboard.getBoolean("enable_compressor");
		
		if(clearAllPCMStickyFaults){
			Robot.compressor.clearAllPCMStickyFaults();
		}
		
		if(enableCompressor){
			Robot.compressor.start();
		}else{
			Robot.compressor.stop();
		}
		
		// motor data
		
		//data that can be gathered (that does not relate to PID and connected encoder function)
		/*
		Robot.drivetrain.front_left_motor.getBrakeEnableDuringNeutral();
		Robot.drivetrain.front_left_motor.getTemp();
		Robot.drivetrain.front_left_motor.getOutputCurrent();
		Robot.drivetrain.front_left_motor.getOutputVoltage();
		Robot.drivetrain.front_left_motor.getBusVoltage();
		Robot.drivetrain.front_left_motor.getFaultOverTemp();
		Robot.drivetrain.front_left_motor.getFaultUnderVoltage();
		Robot.drivetrain.front_left_motor.getFaultHardwareFailure();
		*/
	}
}
