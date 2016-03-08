package org.usfirst.frc.team1124.robot.tools.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents a series of data points. It can find the td offset. This is offset into data points at which the output stablizes.
 * It can also find the slope of the value-timestamp. These values are used to calculate PID coefficients.
 */
public class StepTestSegment {
	public static int STABLITY_SAMPLE_MIN = 5;
	private List<StepTestPoint> data = new ArrayList<StepTestPoint>();
	private Double slope = null;
	private Integer tdOffset = null;

	/**
	 * Instantiate a blank segment.
	 */
	public StepTestSegment() {
	}

	/**
	 * Add a new data point to the the dataset. This does not reset slope or the td offset.
	 * 
	 * @param	p	adds a new point to the end of the dataset.
	 */
	public void addPoint(StepTestPoint p) {
		this.data.add(p);
	}

	/**
	 * Retrieve the point data at i.
	 * 
	 * @param	i	offset
	 * @return	point data at offset i
	 */
	public StepTestPoint getPoint(int i) {
		return this.data.get(i);
	}

	public int size() {
		return this.data.size();
	}

	/**
	 * Figure out at what offset the output stablizes.
	 * 
	 * @return	the offset at which the output stablizes. This caches the offset after figuring it out.
	 */
	public int getTdOffset() {
		if (this.tdOffset ==  null) {
			// 	Find td: how long it took for the second output to stablize, converting to minutes
			int i = 0;
			BigDecimal last = null;
			for (int x=0;x<this.data.size();x++) {
				// Get the output and round to the nearest hundreth
				BigDecimal o = new BigDecimal(this.data.get(x).getOutput()*StepTest.OUTPUT_CONVERSION);
				o.setScale(3,RoundingMode.HALF_UP);
	
				// If there is no last or if the last output doesn't equal the current, reset
				if (last == null || !last.equals(o)) {
					last = o;
					i = 1;
				} else {
					// Increment the number of times in a row we've seen it
					i++;
				}
	
				// If we've seen the same value rounded to the nearest, hundreth at least 3 times, we're considered stable
				if (i >= StepTestSegment.STABLITY_SAMPLE_MIN) {
					this.tdOffset = x - i;
					if (this.tdOffset < 1) {
						this.tdOffset = 1;
					}
					return this.tdOffset.intValue();
				}
			}
			this.tdOffset = data.size()-1;
		}
		return this.tdOffset.intValue();
	}


	/**
	 * Find the slope of the encoder values. Uses line of best fit:
	 * http://hotmath.com/hotmath_help/topics/line-of-best-fit.html
	 * 
	 * @return	the slope of the line that best fits the data points after the output signal stablizes
	 */
	public double getSlope() {
		if (this.slope == null) {
			// Use line of best fit algorithm
			// Start with when we have a steady signal
			int offset = this.getTdOffset();
	
			// Get mean of x values, in this case time
			// Get mean of y values, in this case val
			double X = 0;
			double Y = 0;
			for (int i=offset;i<this.data.size();i++) {
				StepTestPoint point = this.data.get(i);
				X += point.getTimestamp()*StepTest.TIME_CONVERSION;
				Y +=  point.getValue()*StepTest.VALUE_CONVERSION;
			}
			int s = this.data.size() - offset;
			X /= s;
			Y /= s;
	
			// Calculate best fit
			double sXY = 0;
			double sX2 = 0;
			for (int i=offset;i<this.data.size();i++) {
				StepTestPoint point = this.data.get(i);
				double sX = point.getTimestamp()*StepTest.TIME_CONVERSION - X;
				double sY = point.getValue()*StepTest.VALUE_CONVERSION - Y;
				sXY += sX * sY;
				sX2 += sX*sX;
			}
			this.slope = sXY / sX2;
		}
		return this.slope.doubleValue();
	}


	/**
	 * Calculate the period for adjusting the output after changing the signal.
	 */
	public double calculateTd() {
		// Get the stablization offset
		int offset = this.getTdOffset();

		// Subtract the start time from the offset time to get the period
		return (this.getPoint(offset).getTimestamp() - this.getPoint(0).getTimestamp())*StepTest.TIME_CONVERSION;
	}
}
