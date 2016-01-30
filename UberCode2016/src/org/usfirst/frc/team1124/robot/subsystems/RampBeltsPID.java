package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class RampBeltsPID extends PIDSubsystem {
	
	public final static double P = 1;
	public final static double I = 0.01;
	public final static double D = 0;
	
	private CANTalon talon;
//	private DigitalInput lightSensor;
	private Encoder encoder;

    // Initialize your subsystem here
    public RampBeltsPID() {
    	super("RampBelts", P, I, D);
    	
    	talon = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));
    	//lightSensor = new DigitalInput(Robot.configIO.getIntVal("ramp_belts_light_sensor"));
    	
    	int port_a = Robot.configIO.getIntVal("ramp_talon_enc_a");
		int port_b = Robot.configIO.getIntVal("ramp_talon_enc_b");
		
		encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);

		setSetpoint(0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
//    public boolean getSensorState(){
//    	return lightSensor.get();
//    }
    
    public double getEncoderRate(){
    	return encoder.getRate();
    }
    
    public void homeEncoder(){
		encoder.reset();
	}
    
    protected double returnPIDInput() {
        return encoder.getRate();
    }
    
    protected void usePIDOutput(double output) {
        talon.set(output);
    }
}
