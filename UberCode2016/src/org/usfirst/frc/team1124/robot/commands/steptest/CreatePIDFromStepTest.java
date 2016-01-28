package org.usfirst.frc.team1124.robot.commands.steptest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import edu.wpi.first.wpilibj.command.Command;
import java.util.*;

/**
 * StepTestDriveTrain
 * 
 * This command analyzes step data in the form of a series of steps.
 * Data points are represented as time in seconds, output, and value.
 * 
 * @author	Mrs. Weston
 */
public class CreatePIDFromStepTest extends Command {
	private List<List<double[]>> data = null;
	private Double p = null;
	private Double i = null;
	private Double d = null;

	public CreatePIDFromStepTest(List<List<double[]>> data) {
		this.data = data;
	}

	@Override
	protected void initialize() {
	}

	/**
	 * Analyze the step data to calculate the PID coefficients.
	 */
	@Override
	protected void execute() {
		// Calculate PID coefficients
		double[] m = new double[this.data.size()];
		for (int i=0;i<this.data.size();i++) {
			// Find slope of each line
			m[i] = this.getSlope(this.data.get(i));
		}
		// For each transition:
		double td = 0;
		double ri = 0;
		for (int i=1;i<this.data.size();i++) {
			ri += this.calculateRi(this.data.get(i-1),this.data.get(i),m[i-1],m[i]);
			td += this.calculateTd(this.data.get(i));
		}

		// average td(s) and ri(s)
		int p = this.data.size()-1;
		td /= p;
		ri /= p;

		// Calculate constants for each side
		this.p = 0.75 / (ri * td);
		this.i = 5 * td;
		this.d = 0.4 * td;
	}

	/**
	 * Check if the PID coefficients have been calculated.
	 */
	@Override
	protected boolean isFinished() {
		return (this.p != null && this.i != null && this.d != null);
	}

	/**
	 * Calculate ri based on the data between two test segments.
	 * 
	 * @param	seg1
	 * @param	seg2
	 * @param	m1
	 * @param	m2
	 * @param	output1
	 * @param	output2
	 */
	private double calculateRi(List<double[]> seg1, List<double[]> seg2,double m1,double m2) {
		// 	Find the slope difference between two consecutive tests
		double dm = m2 - m1;

		// Find the min and max of each segment
		int offset1 = this.findTdOffset(seg1);
		Double p1a = seg1.get(offset1)[2];
		Double t1 = seg1.get(seg1.size()-1)[0] - seg1.get(offset1)[0];
		Double p1b = (m1 * t1) + p1a; // theoretical p1b based on y = mx+b
		Double min1;
		Double max1;
		if (p1a > p1b) {
			min1 = p1b;
			max1 = p1a;
		} else {
			min1 = p1a;
			max1 = p1b;
		}
		int offset2 = this.findTdOffset(seg2);
		Double p2a = seg2.get(offset2)[2];
		Double t2 = seg2.get(seg2.size()-1)[0] - seg2.get(offset2)[0];
		Double p2b = (m2 * t2) + p2a; // theoretical p2b based on y = mx+b
		Double min2;
		Double max2;
		if (p2a > p2b) {
			min2 = p2b;
			max2 = p2a;
		} else {
			min2 = p2a;
			max2 = p2b;
		}

		// 	Calculate the slope perecentage using the max and min values of the slope
		Double min = (min1 < min2) ? min1 : min2;
		Double max = (max1 > max2) ? max1 : max2;
		double dmp = (100 * dm)/(max - min);

		// 	Get the change in controller outputs between each difference
		double output1 = seg1.get(offset1)[1];
		double output2 = seg2.get(offset2)[1];
		double cod = output2 - output1;

		// 	Calculate ri
		return dmp/cod;
	}

	/**
	 * Figure out at what offset the output stablizes.
	 * 
	 * @param	seg  	data to check
	 * @param	right	if true, check right side stats. If false, check left side stats.
	 */
	private int findTdOffset(List<double[]> seg) {
		// 	Find td: how long it took for the second output to stablize, converting to minutes
		int i = 0;
		BigDecimal last = null;
		for (int x=0;x<seg.size();x++) {
			// Get the output and round to the nearest hundreth
			BigDecimal o = new BigDecimal(seg.get(x)[1]);
			o.setScale(2,RoundingMode.HALF_UP);

			// If there is no last or if the last output doesn't equal the current, reset
			if (last == null || !last.equals(o)) {
				last = o;
				i = 1;
			} else {
				// Increment the number of times in a row we've seen it
				i++;
			}

			// If we've seen the same value rounded to the nearest, hundreth at least 3 times, we're considered stable
			if (i >= 3) {
				return x;
			}
		}
		return seg.size()-1;
	}

	/**
	 * Calculate the period for adjusting the output after changing the signal.
	 * 
	 * @param	seg   	data to analyze
	 */
	private double calculateTd(List<double[]> seg) {
		// Get the stablization offset
		int offset = this.findTdOffset(seg);

		// Subtract the start time from the offset time to get the period
		return (seg.get(offset)[0] - seg.get(0)[0])/60; // divide by 60 to convert to minutes
	}

	/**
	 * Find the slope of the encoder values. Uses line of best fit:
	 * http://hotmath.com/hotmath_help/topics/line-of-best-fit.html
	 * 
	 * @param	seg   	data to analyze
	 */
	private double getSlope(List<double[]> seg) {
		// Use line of best fit algorithm
		// Start with when we have a steady signal
		int offset = this.findTdOffset(seg);

		// Get mean of x values, in this case time
		// Get mean of y values, in this case val
		double X = 0;
		double Y = 0;
		for (int i=offset;i<seg.size();i++) {
			double[] point = seg.get(i);
			X += point[0];
			Y +=  point[2];
		}
		int s = seg.size() - offset;
		X /= s;
		Y /= s;

		// Calculate best fit
		double sXY = 0;
		double sX2 = 0;
		for (int i=offset;i<seg.size();i++) {
			double[] point = seg.get(i);
			double sX = point[0] - X;
			double sY = point[2] - Y;
			sXY += sX * sY;
			sX2 += sX*sX;
		}
		double m = sXY / sX2;
		return m;
	}

	@SuppressWarnings("unused")
	private double deprecatedGetSlope(List<double[]> seg) {
		// Using a tree map to store slope => count because it automatically sorts keys.
		// This is useful later when we're looking for median.
		Map<Double,Integer> mMap = new TreeMap<Double,Integer>();
		for (int i=1;i<seg.size();i++) {
			// Check one point to the next
			double dt = seg.get(i)[0]-seg.get(i-1)[0];
			double dy = seg.get(i)[2]-seg.get(i-1)[2];

			// Calculate the slope and round to the nearest hundreth
			BigDecimal m = new BigDecimal(dy/dt);
			m.setScale(2,RoundingMode.HALF_UP);
			Double md = m.doubleValue();

			// Increment the number of times the rounded slope has shown up
			if (mMap.containsKey(md)) {
				int v = mMap.get(md)+1;
				mMap.put(md,v);
			} else {
				mMap.put(md,1);
			}
		}

		// Find median slope
		int c = 0;
		int target = mMap.size()/2; // halfway point
		Double medianSlope = null;
		for (Double m : mMap.keySet()) {
			// Move to the next slope
			c += mMap.get(m);

			// If we've counted at least halfway, we're at the median.
			if (c >= target) {
				medianSlope = m;
				break;
			}
		}
		return medianSlope;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	public double getP() {
		return this.p;
	}

	public double getI() {
		return this.i;
	}

	public double getD() {
		return this.d;
	}

	/**
	 * Tests the analysis portion of the command.
	 */
	public void main(String[] args) {
		List<List<double[]>> data = new ArrayList<List<double[]>>();
		// TODO: fake data to test
		CreatePIDFromStepTest test = new CreatePIDFromStepTest(data);
		test.execute();

		// check coefficients
		System.out.println("P: "+test.getP());
		System.out.println("I: "+test.getI());
		System.out.println("D: "+test.getD());
	}
}
