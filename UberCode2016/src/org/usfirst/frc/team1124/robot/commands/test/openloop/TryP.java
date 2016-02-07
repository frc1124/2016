package org.usfirst.frc.team1124.robot.commands.test.openloop;

import org.usfirst.frc.team1124.robot.Robot;
import edu.wpi.first.wpilibj.command.PIDCommand;

@SuppressWarnings("unused")
public abstract class TryP extends PIDCommand {
	private boolean foundStableP = false;
	private double p = 0.0;
	private double i = 0.0;
	private double d = 0.0;
	protected double setpoint;
	protected double speed = 0.0;
	private int passes = 0;
	private double[] peak = new double[20];
	private long[] peakTimestamp = new long[20];
	private int gc = 0;
	private double[] gain = null;
	private double periodGain = 0;
	private long lastPeakTimestamp = 0;
	private double lastValue1 = 0.0;
	private long lastTimestamp1 = 0;
	private double lastValue2 = 0.0;
	private long lastTimestamp2 = 0;

	public TryP(double p) {
		super(p,0.0,0.0);
		this.p = p;
	}

	@Override
	protected void initialize() {
    	Robot.drivetrain.resetEncoders();
		this.setSetpoint(setpoint);
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

	public boolean hasStableP() {
		// If assessed, return flag
		if (this.gain != null) {
			return this.foundStableP;
		}

		// If we don't have enough sample, return no
		if (this.gc < 20) {
			return this.foundStableP;
		}

		// Inspect data to determine if we have a stable pattern of peaks within tolerance
		this.gain = new double[10];
		for (int i=0;i<20;i+=2) {
			double peak1 = Math.abs(this.peak[i]);
			double peak2 = Math.abs(this.peak[i+1]);
			gain[i] = peak1 + peak2;
		}

		// Check differences in gain
		double tolerance = 0.0226;
		for (int i=0;i<10;i+=2) {
			double gain1 = gain[i];
			double gain2 = gain[i+1];
			if (Math.abs(gain2 - gain1) > tolerance) {
				return false;
			}
		}
		this.foundStableP = true;
		return this.foundStableP;
	}

	@Override
	protected void execute() {
		// Keep tabs on current encoder value relative to setpoint
		double value = this.returnPIDInput();
		long timestamp = System.currentTimeMillis();

		// Determine if overshot the setpoint
		if (this.lastTimestamp1 > 0 && ((value > setpoint && this.setpoint > this.lastValue1) || (value < setpoint && this.lastValue1 < this.setpoint))) {
			this.passes++;
		} else {
			double e = Math.abs(this.setpoint - value);
			double e1 = Math.abs(this.setpoint - this.lastValue1);
			double e2 = Math.abs(this.setpoint - this.lastValue2);

			// Look for a peak
			if (e1 > e && e1 > e2) {
				this.peak[this.gc] = e1;
				this.peakTimestamp[this.gc] = lastTimestamp1;
				this.gc++;
			}
		}

		// Move the values along for the next pass
		this.lastTimestamp2 = this.lastTimestamp1;
		this.lastValue2 = this.lastValue1;
		this.lastTimestamp1 = timestamp;
		this.lastValue1 = value;
	}

	@Override
	protected boolean isFinished() {
		// Robot has stopped moving or we've found a stable pattern
		return (!this.isMoving() || this.hasStableP());
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void usePIDOutput(double output) {
		this.speed = output;
	}

	abstract protected double returnPIDInput();
	abstract protected boolean isMoving();
}
