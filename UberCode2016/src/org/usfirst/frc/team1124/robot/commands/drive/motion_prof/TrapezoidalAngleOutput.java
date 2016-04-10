package org.usfirst.frc.team1124.robot.commands.drive.motion_prof;

import java.util.ArrayList;

import org.usfirst.frc.team1124.robot.Robot;
import org.usfirst.frc.team1124.robot.tools.vision.VisionTools;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Trapezoidal motion profiled angle control
 */
public class TrapezoidalAngleOutput extends Command {

	Timer t = new Timer();
	
	private double distance = 0;
	
	// constants
	private final double v_max = 60.0;
	private final double filter_time_1 = 0.200;
	private final double filter_time_2 = 0.100;
	//private final double filter_time_1 = 0.050;
	//private final double filter_time_2 = 0.025;
	private double itp = 0.020;
	
	// system variables
	private double fl_1 = Math.ceil(filter_time_1 / itp);
	private double fl_2 = Math.ceil(filter_time_2 / itp);
	
	// calculated variables
	private double t_4 = 0.0;
	private int n = 0;
	private int sign = 0;
	
	// iteration data
	private int step = 1;
	
	// results
	private double position = 0.0;
	private double velocity = 0.0;
	private double acceleration = 0.0;
	
	// execution variables
	private double time;
	private double input;
	
	private double fl1_sum;
	private double prev_fl1_sum;
	private double fl2_sum;
	
	private double prev_velocity = 0;
	private double prev_position = 0;
	
	// pid
	private AngularRatePID pid = new AngularRatePID();
	private double output = 0;
	
	private ArrayList<Double> filter_1_data = new ArrayList<Double>();
	
    public TrapezoidalAngleOutput() {
        requires(Robot.drivetrain);
    }
    
    protected void initialize() {
    	t.start();
    	
    	pid.start();
    	
    	try{
    		/*
	    	double xlhsGoalBBox = SmartDashboard.getNumber("vision_target_left");
	    	double widthGoalBBox = SmartDashboard.getNumber("vision_target_width");
	    	
	    	System.out.println("Target Left X: " + xlhsGoalBBox + " Width: " + widthGoalBBox);
	    	*/
    		
    		double x_cm = SmartDashboard.getNumber("vision_target_x_cm");
    		
	    	distance = VisionTools.turnAngle(x_cm);
	    	
	    	distance = 5.0;

	    	sign = (int) Math.signum(distance);
	    	distance = Math.abs(distance);
	    	
	    	System.out.println("Distance: " + distance);
    	}catch(Exception oh_no){
    		System.out.println("Fatal Targeting Error: Dashboard data not found.");
    	}
    	
    	t_4 = distance / v_max;
    	n = (int) (t_4 / itp);
    	
    	System.out.println("t_4: " + t_4 + "	n: " + n);
    }
    
    private long prev_time = System.currentTimeMillis();
    private long deltaTime = 0;
    
    protected void execute() {
    	time = t.get();
    	
    	deltaTime = System.currentTimeMillis() - prev_time;
    	prev_time = System.currentTimeMillis();
    	
    	System.out.println(deltaTime);
    	
    	itp = (((double) deltaTime) / 1000);
    	
    	if(time >= step * itp){
    		//=MAX(0,	MIN(1,	(prev_f1_sum + IF((input == 1),		(1/FL1),	(-1/FL1)	)	)	)	)
    		
    		input = (step < n + 2) ? 1 : 0;
    		
    		if(input == 1){
    			fl1_sum = Math.max(0, Math.min(1, prev_fl1_sum + (1 / fl_1)));
    		}else{
    			fl1_sum = Math.max(0, Math.min(1, prev_fl1_sum + (-1 / fl_1)));
    		}
    		
    		filter_1_data.add(fl1_sum);
    		
    		//=SUM(	OFFSET(	D14,((-1 * MIN(fl_2,step)) + 1), 0, MIN(fl_2,step), 1	)	)
    		
    		fl2_sum = 0;
    		
    		for(int i = 0; i < Math.min(fl_2, step); i++){
    			fl2_sum += filter_1_data.get( ((filter_1_data.size() - 1) + (-1 * Math.min((int) fl_2, step)) + 1));
    		}
    		
    		velocity = sign * (fl1_sum + fl2_sum) / (1 + fl_2) * v_max;
    		
    		position = (((velocity + prev_velocity) / 2) * itp) + prev_position;
    		
    		acceleration = (velocity - prev_velocity) / itp;
    		
    		System.out.println("Step #" + step + "	Filter 1 Sum: " + fl1_sum + "	Filter 2 Sum: " + fl2_sum + "	Position: " + position + "	Velocity: " + velocity + "	Acceleration: " + acceleration);
    		//System.out.println("Step #" + step + "	" + fl1_sum + "	" + fl2_sum + "	" + position + "	" + velocity + "	" + acceleration);
    		
    		pid.setSetpoint(velocity);
    		
    		step++;
    		
    		prev_fl1_sum = fl1_sum;
    		
    		prev_velocity = velocity;
    		prev_position = position;
    	}
    	
    	output = pid.getOutput();
		
		Robot.drivetrain.drive_tank_auto(output, (-1) * output);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	step = 1;
    	
    	position = 0;
    	velocity = 0;
    	acceleration = 0;
    	
    	filter_1_data.clear();
    	
    	prev_velocity = 0;
    	prev_position = 0;
    	
    	t.reset();
    	
    	pid.cancel();
    }
    
    protected void interrupted() {
    	end();
    }
}
