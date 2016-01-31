package org.usfirst.frc.team1124.robot.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.usfirst.frc.team1124.robot.commands.steptest.CreatePIDFromStepTest;

/**
 * This represents the data from a step test.
 * 
 * @author Mrs. Weston
 */
public class StepTest {
	private List<StepTestSegment> data = new ArrayList<StepTestSegment>();
	private int size = 0;
	private Double p = null;
	private Double i = null;
	private Double d = null;

	public StepTest() {
	}

	/**
	 * Retrieves the P coefficient. If it hasn't been calculated, it performs the analysis.
	 * 
	 * @return	p coefficient based on test
	 */
	public double getP() {
		if (this.p == null) {
			this.calculatePID();
		}
		return this.p;
	}

	/**
	 * Retrieves the I coefficient. If it hasn't been calculated, it performs the analysis.
	 * 
	 * @return	i coefficient based on test
	 */
	public double getI() {
		if (this.i == null) {
			this.calculatePID();
		}
		return this.i;
	}

	/**
	 * Retrieves the D coefficient. If it hasn't been calculated, it performs the analysis.
	 * 
	 * @return	d coefficient based on test
	 */
	public double getD() {
		if (this.d == null) {
			this.calculatePID();
		}
		return this.d;
	}

	/**
	 * Adds storage for the next segment and increments the size.
	 */
	public void changeSignal() {
		this.data.add(new StepTestSegment());
		this.size++;
	}

	/**
	 * If we don't have storage, add it. Add the point to the current data set.
	 * 
	 * @param	p	data point
	 */
	public void addPoint(StepTestPoint p) {
		if (size == 0) {
			this.changeSignal();
		}
		this.data.get(this.size-1).addPoint(p);
	}

	/**
	 * Calculates the PID coefficient .
	 */
	public void calculatePID() {
		// Calculate PID coefficients
		// For each transition:
		double td = 0;
		double ri = 0;
		for (int i=1;i<this.data.size();i++) {
			ri += this.calculateRi(this.data.get(i-1),this.data.get(i));
			td += this.data.get(i).calculateTd();
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
	 * Calculate ri based on the data between two test segments.
	 * 
	 * @param	seg1
	 * @param	seg2
	 */
	private double calculateRi(StepTestSegment seg1, StepTestSegment seg2) {
		// 	Find the slope difference between two consecutive tests
		double dm = seg2.getSlope() - seg1.getSlope();

		// Find the min and max of each segment
		int offset1 = seg1.getTdOffset();
		Double p1a = seg1.getPoint(offset1).getValue();
		Double t1 = seg1.getPoint(seg1.size()-1).getTimestamp() - seg1.getPoint(offset1).getTimestamp();
		Double p1b = (seg1.getSlope() * t1) + p1a; // theoretical p1b based on y = mx+b
		Double min1;
		Double max1;
		if (p1a > p1b) {
			min1 = p1b;
			max1 = p1a;
		} else {
			min1 = p1a;
			max1 = p1b;
		}
		int offset2 = seg2.getTdOffset();
		Double p2a = seg2.getPoint(offset2).getValue();
		Double t2 = seg2.getPoint(seg2.size()-1).getTimestamp() - seg2.getPoint(offset2).getTimestamp();
		Double p2b = (seg2.getSlope() * t2) + p2a; // theoretical p2b based on y = mx+b
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
		double output1 = seg1.getPoint(offset1).getOutput();
		double output2 = seg2.getPoint(offset2).getOutput();
		double cod = output2 - output1;

		// 	Calculate ri
		return dmp/cod;
	}

	/**
	 * Tests the analysis portion of the command.
	 */
	public static void main(String[] args) {
		StepTest data = new StepTest();
	
		// Read in log
		String line;
		try {
			InputStream fis = new FileInputStream("/Users/jenniferweston/git/2016/drivetrain-right-2016-31-31-06-01.log");
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			double last = 5;
		    while ((line = br.readLine()) != null) {
				String[] s = line.split("\t");
				double o = new Double(s[1]).doubleValue();
				if (o == 0.0) {
					continue;
				}
				double t = new Double(s[0]).doubleValue();
				double v = new Double(s[2]).doubleValue();
				StepTestPoint p = new StepTestPoint(t,o,v);
				if (t < last) {
					data.changeSignal();
				}
				last = t;
				data.addPoint(p);
		    }

		    // check coefficients
			System.out.println("P: "+data.getP());
			System.out.println("I: "+data.getI());
			System.out.println("D: "+data.getD());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
