package org.usfirst.frc.team1124.robot;

import java.awt.geom.Point2D;

public class PositionTracker {
	private Point2D.Double position = new Point2D.Double(0,0);
	private double angle = 0;
	private double leftEncoder = 0;
	private double rightEncoder = 0;

	public PositionTracker() {
	}

	public PositionTracker(int initKnownLocation) {
	}

	public PositionTracker(double x, double y, double angle) {
		this.position.setLocation(x,y);
		this.angle = angle;
	}

	public PositionTracker(Point2D.Double initKnownLocation,double angle,double leftEncoder,double rightEncoder) {
		this.setPosition(initKnownLocation);
		this.angle = angle;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
	}

	public PositionTracker(double x, double y, double angle, double leftEncoder,double rightEncoder) {
		this.position.setLocation(x,y);
		this.angle = angle;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
	}

	/**
	 * @return the position
	 */
	public Point2D.Double getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * @return the leftEncoder
	 */
	public double getLeftEncoder() {
		return leftEncoder;
	}

	/**
	 * @param leftEncoder the leftEncoder to set
	 */
	public void setLeftEncoder(double leftEncoder) {
		this.leftEncoder = leftEncoder;
	}

	/**
	 * @return the rightEncoder
	 */
	public double getRightEncoder() {
		return rightEncoder;
	}

	/**
	 * @param rightEncoder the rightEncoder to set
	 */
	public void setRightEncoder(double rightEncoder) {
		this.rightEncoder = rightEncoder;
	}

	public void updateFromEncoders(double leftEncoder,double rightEncoder) {
		/* Use the existing left and right encoder values to find the difference in position of the tank treads. Based on the change,
		 * figure out the affect this has on the movement of the drivetrain and update position.
		 */
		// Find the change in position based on the current angle and the distance both encoders traveled
		double de = rightEncoder - leftEncoder;
		double bothTraveled = this.leftEncoder - leftEncoder + de;
		double dx = this.position.getX()+bothTraveled*Math.sin(this.angle);
		double dy = this.position.getY()+bothTraveled*Math.cos(this.angle);
		this.position.setLocation(this.position.getX()+dx,this.position.getY()+dy);

		// Find the change in angle based on the difference between the change in encoders
		this.angle += Math.asin(de);

		// Update the encoders
		this.setLeftEncoder(leftEncoder);
		this.setRightEncoder(rightEncoder);
	}
}
