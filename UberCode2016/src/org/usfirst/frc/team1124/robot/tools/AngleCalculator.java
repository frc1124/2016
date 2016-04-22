package org.usfirst.frc.team1124.robot.tools;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author FRC Team #125, NUtrons
 *
 */
public class AngleCalculator {
	private static final double CAMERA_X_OFFSET = -3.0;
	private static final double CAMERA_Y_OFFSET = 0.0;
	private static final double CAMERA_Z_OFFSET = 42.5;
	private static final double CAMERA_MOUNT_ANGLE = 28.0;
	
	private static final double CAMERA_PIXEL_WIDTH = 320.0;
	private static final double CAMERA_PIXEL_HEIGHT = 240.0;
	
	private static final double CAMERA_FOV = 47.0;
	private static final double CAMERA_VERTICAL_FOV = CAMERA_FOV * CAMERA_PIXEL_HEIGHT / CAMERA_PIXEL_WIDTH;
	
	private static final double TARGET_HEIGHT_ON_WALL = 90.0;
	private static final double TARGET_HEIGHT = 14.0;
	
	/**
	 * @param pixelWidth width of the target, in pixels, on the image
	 * @param centerX horizontal position of the target, in pixels, on the image
	 * @return the horizontal angle to the target from the robot's center
	 */
	public static double getHorizontalAngle(double pixelWidth, double centerX){
		double measuredDistance = getCameraDist(pixelWidth);
		SmartDashboard.putNumber("cameraMeasuredDistance", measuredDistance);
		
    	double distance = getRobotDist(measuredDistance, getHorizontalCameraAngle(centerX));
    	SmartDashboard.putNumber("robotPredictedDistance", distance);
    	
    	return Math.toDegrees(Math.acos((Math.pow(CAMERA_X_OFFSET, 2) - Math.pow(measuredDistance, 2) + Math.pow(distance, 2)) / (2 * distance * Math.abs(CAMERA_X_OFFSET)))) - 90.0;
    }
	
	/**
	 * EXPERIMENTAL
	 * @param centerY vertical position of the target, in pixels, on the image
	 * @param centerX horizontal position of the target, in pixels, on the image
	 * @return the horizontal angle to the target from the robot's center
	 */
	public static double getHorizontalAngleUsingYPos(double centerX, double centerY){
		// TODO calculate height from arm angle instead of using constant
		double measuredDistance = getCameraDistUsingAngle(getVerticalCameraAngle(centerY) + CAMERA_MOUNT_ANGLE);
		SmartDashboard.putNumber("cameraMeasuredDistanceUsingYPos", measuredDistance);
    	
		double distance = getRobotDist(measuredDistance, getHorizontalCameraAngle(centerX));
    	SmartDashboard.putNumber("robotPredictedDistanceUsingYPos", distance);
    	
    	return Math.toDegrees(Math.acos((Math.pow(CAMERA_X_OFFSET, 2) - Math.pow(measuredDistance, 2) + Math.pow(distance, 2)) / (2 * distance * Math.abs(CAMERA_X_OFFSET)))) - 90.0;
    }
	
	/**
	 * @param x horizontal position of target, in pixels, on the image
	 * @return horizontal position of target in degrees
	 */
	public static double getHorizontalCameraAngle(double x){
		double slope = CAMERA_FOV / CAMERA_PIXEL_WIDTH;
	    double intercept = -CAMERA_FOV / 2;
	    return x * slope + intercept;
	}
	
	/**
	 * @param y vertical position of target, in pixels, on the image
	 * @return vertical position of target in degrees
	 */
	public static double getVerticalCameraAngle(double y){
    	double slope = -CAMERA_VERTICAL_FOV / CAMERA_PIXEL_HEIGHT;
        double intercept = CAMERA_VERTICAL_FOV / 2;
        return y * slope + intercept;
    }
	
    /**
     * @param yAngle angle from the horizontal plane to the target
     * @return the distance to the target
     */
    public static double getCameraDistUsingAngle(double yAngle){
    	return (TARGET_HEIGHT_ON_WALL - CAMERA_Z_OFFSET)/Math.tan(Math.toRadians(yAngle));
    }
    
	public static double getCameraDist(double targetPixelWidth){
		double targetAngularWidth = targetPixelWidth * CAMERA_FOV / CAMERA_PIXEL_WIDTH;
		
		return (TARGET_HEIGHT / 2.0)/Math.tan(Math.toRadians(targetAngularWidth/2.0));
	}
    
	/**
     * @param cameraDistance the distance from the camera to the target
     * @param cameraXAngle horizontal angle to the target from the camera
     * @return the Robot's distance to the target
     */
	@SuppressWarnings("unused")
	private static double getRobotDist(double cameraDistance, double cameraXAngle){
		double angle = 90 + ((CAMERA_X_OFFSET > 0) ? cameraXAngle : -cameraXAngle);
		
    	if(CAMERA_X_OFFSET == 0.0){
    		return cameraDistance;
    	}
    	
    	return Math.sqrt(Math.pow(CAMERA_X_OFFSET, 2) + Math.pow(cameraDistance, 2) - (2 * Math.abs(CAMERA_X_OFFSET) * cameraDistance * Math.cos(Math.toRadians(angle))));
    }
    
    public static void main(String[] args){
    	double cameraDistance = getCameraDist(320);
    	System.out.println(cameraDistance);
    }
}