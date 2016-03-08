package org.usfirst.frc.team1124.robot.tools.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

	public static double VALUE_CONVERSION = 1.0;
//	public static double TIME_CONVERSION = 1.0/3600.0;
//	public static double TIME_CONVERSION = 1.0/60.0;
	public static double TIME_CONVERSION = 1.0;
	public static double OUTPUT_CONVERSION = 4.5/100.0; // output is given as percentage of bus voltage which operates at 4.5V

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
		int p = 0;
		for (int i=1;i<this.data.size();i++) {
			StepTestSegment seg1 = this.data.get(i-1);
			StepTestSegment seg2 = this.data.get(i);
			if (seg1.size() < 2 || seg2.size() < 2) {
				continue;
			}
			ri += this.calculateRi(seg1,seg2);
			td += seg2.calculateTd();
			p++;
		}

		// average td(s) and ri(s)
		td /= p;
		ri /= p;

		// Calculate constants for each side
		this.p = Math.abs(0.75 / (ri * td));
		this.i = Math.abs(5 * td);
		this.d = Math.abs(0.4 * td);
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
		Double p1a = seg1.getPoint(offset1).getValue()*StepTest.VALUE_CONVERSION;
		Double t1 = (seg1.getPoint(seg1.size()-1).getTimestamp() - seg1.getPoint(offset1).getTimestamp())*StepTest.TIME_CONVERSION;
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
		Double p2a = seg2.getPoint(offset2).getValue()*StepTest.VALUE_CONVERSION;
		Double t2 = (seg2.getPoint(seg2.size()-1).getTimestamp() - seg2.getPoint(offset2).getTimestamp())*StepTest.TIME_CONVERSION;
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
		double output1 = seg1.getPoint(offset1).getOutput()*StepTest.OUTPUT_CONVERSION;
		double output2 = seg2.getPoint(offset2).getOutput()*StepTest.OUTPUT_CONVERSION;
		double cod = output2 - output1;

		// 	Calculate ri
		return dmp/cod;
	}

	private static void runTest(String filename) {	
		StepTest data = new StepTest();

		// Read in log
		String line;
		try {
			InputStream fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
		    while ((line = br.readLine()) != null) {
				String[] s = line.split("\t");
				double o = new Double(s[1]).doubleValue();
				if (o == 0.0) {
					continue;
				}
				double t = new Double(s[0]).doubleValue();
				double v = new Double(s[2]).doubleValue();
				StepTestPoint p = new StepTestPoint(t,o,v);
				int period = ((int)t)/5;
				if (period >= data.size) {
					data.changeSignal();
				}
				data.addPoint(p);
		    }

		    // check coefficients
			System.out.println("P: "+data.getP());
			BigDecimal f = new BigDecimal(data.getI());
			System.out.println("I: "+f.toPlainString());
			f = new BigDecimal(data.getD());
			System.out.println("D: "+f.toPlainString());
			isr.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Tests the analysis portion of the command.
	 */
	public static void main(String[] args) {
		String leftFile = "/Users/jenniferweston/git/2016/drivetrain-left.log";
		String rightFile = "/Users/jenniferweston/git/2016/drivetrain-right.log";
		System.out.println("Left: ");
		StepTest.runTest(leftFile);
		System.out.println();
		System.out.println("Right: ");
		StepTest.runTest(rightFile);
	}
}
