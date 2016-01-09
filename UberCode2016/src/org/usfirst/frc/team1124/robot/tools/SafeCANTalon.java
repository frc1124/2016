package org.usfirst.frc.team1124.robot.tools;

import edu.wpi.first.wpilibj.CANTalon;

public class SafeCANTalon extends CANTalon{
	
	public SafeCANTalon(int deviceNumber) {
		super(deviceNumber);
		// TODO Auto-generated constructor stub
	}

	public SafeCANTalon(int deviceNumber, int controlPeriodMs) {
		super(deviceNumber, controlPeriodMs);
		// TODO Auto-generated constructor stub
	}

	
}
