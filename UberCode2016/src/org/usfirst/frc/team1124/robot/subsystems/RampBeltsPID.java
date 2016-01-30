package org.usfirst.frc.team1124.robot.subsystems;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.commands.belts.RampHoldPosition;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The belts inside the robot to store the ball and feed it to the shooter
 */
public class RampBeltsPID extends PIDSubsystem {
	
	public final static double P = 1;
	public final static double I = 0.01;
	public final static double D = 0;
	
	private CANTalon talon;
	private Encoder encoder;

    public RampBeltsPID() {
    	super("RampBelts", P, I, D);
    	
    	talon = new CANTalon(Robot.configIO.getIntVal("ramp_conveyor_talon"));
    	
    	int port_a = Robot.configIO.getIntVal("ramp_talon_enc_a");
		int port_b = Robot.configIO.getIntVal("ramp_talon_enc_b");
		
		encoder = new Encoder(port_a, port_b, false, EncodingType.k4X);

		setSetpoint(0);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new RampHoldPosition());
    }
    
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
