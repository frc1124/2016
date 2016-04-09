package org.usfirst.frc.team1124.robot.commands.test;

import java.util.ArrayList;
import java.util.Timer;

import org.usfirst.frc.team1124.robot.tools.vision.VisionTools;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrapezoidalAngleOutputTest {
	
	private static int t = 0;
	
	private static double distance = 0;
	
	// constants
	private static final double v_max = 30.0;
	private static final double filter_time_1 = 0.200;
	private static final double filter_time_2 = 0.100;
	private static final double itp = 0.020;
	
	// system variables
	private static double fl_1 = Math.ceil(filter_time_1 / itp);
	private static double fl_2 = Math.ceil(filter_time_2 / itp);
	
	// calculated variables
	private static double t_4 = 0.0;
	private static int n = 0;
	private static int sign = 0;
	
	// iteration data
	private static int step = 1;
	
	// results
	private static double position = 0.0;
	private static double velocity = 0.0;
	private static double acceleration = 0.0;
	
	// execution variables
	private static double time;
	private static double input;
	
	private static double fl1_sum;
	private static double prev_fl1_sum;
	private static double fl2_sum;
	private static double prev_fl2_sum;
	
	private static double prev_velocity = 0;
	private static double prev_position = 0;
	
	private static ArrayList<Double> filter_1_data = new ArrayList<Double>();
	
	private static boolean run = true;
	
	public static void main(String args[]){
		init();
		
		while(run){
			execute();
		}
		
		end();
	}
	
	private static void init(){
		t = 0;
    	
    	try{
    		/*
	    	double xlhsGoalBBox = SmartDashboard.getNumber("vision_target_left");
	    	double widthGoalBBox = SmartDashboard.getNumber("vision_target_width");
	    	
	    	System.out.println("Target Left X: " + xlhsGoalBBox + " Width: " + widthGoalBBox);
	    	*/
    		
    		double x_cm = 120;// = SmartDashboard.getNumber("vision_target_x_cm");
    		
	    	distance = VisionTools.turnAngle(x_cm);
	    	
	    	System.out.println("Distance: " + distance);

	    	sign = (int) Math.signum(distance);
	    	distance = Math.abs(distance);
    	}catch(Exception oh_no){
    		System.out.println("Fatal Targeting Error: Dashboard data not found.");
    	}
    	
    	t_4 = distance / v_max;
    	n = (int) (t_4 / itp);
    	
    	System.out.println("t_4: " + t_4 + " n: " + n);
	}
	
	private static void execute(){
		time = t;
    	
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
    			fl2_sum += filter_1_data.get((int) ((filter_1_data.size() - 1) + (-1 * Math.min(fl_2, step)) + 1));
    		}
    		
    		velocity = sign * (fl1_sum + fl2_sum) / (1 + fl_2) * v_max;
    		
    		position = (((velocity + prev_velocity) / 2) * itp) + prev_position;
    		
    		acceleration = ((((velocity - prev_velocity)) / itp));
    		
    		//System.out.println("Step #" + step + "	Filter 1 Sum: " + fl1_sum + "	Fitler 2 Sum: " + fl2_sum + "	Position: " + position + "	Velocity: " + velocity + "	Acceleration: " + acceleration);
    		System.out.println("Step #" + step + "	" + fl1_sum + "	" + fl2_sum + "	" + position + "	" + velocity + "	" + acceleration);
    		
    		step++;
    		
    		prev_fl1_sum = fl1_sum;
    		prev_fl2_sum = fl2_sum;
    		
    		if(velocity == 0 && step > 2){
    			run = false;
    		}
    		
    		prev_velocity = velocity;
    		prev_position = position;
    	}
    	
    	t++;
	}
	
	private static void end(){
    	step = 1;
    	
    	position = 0;
    	velocity = 0;
    	acceleration = 0;
    	
    	prev_velocity = 0;
    	prev_position = 0;
    	
    	filter_1_data.clear();
    	
    	t = 0;
	}
}
