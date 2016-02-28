package org.usfirst.frc.team1124.robot.tools;

public class VisionTools {
	private static final double cameraWidth = 320;
	private static final double cameraHeight = 240;
	private static final double tanCameraMountAngle = 28.0 * Math.PI / 180;
	private static final double viewAngleHoriz = 47 * Math.PI / 180;
	private static final double focalLength = cameraWidth / 2 / Math.tan(viewAngleHoriz / 2);
	private static final double goalWidth = 20;   //actual--I've just forgotten the number
	private static final double goalBottomHeight = 83;
	private static final double goalTopHeight = 97;
	private static final double cameraMountHeight = 42; //41.523;
	private static final double dHBottom = goalBottomHeight - cameraMountHeight;
	private static final double dHTop = goalTopHeight - cameraMountHeight;
	
//	private static final double cameraCoRdist = #;
//	double TLX;
//	double TLY;
//	double BLX;
//	double BLY;
//  double TRX;
//  double TRY;
//  double BRX;
//  double BRY;
//  double dLeft = (-32.029669883833 * (TLY + 517.35106259548) / (TLY - 332.4503541985) - 23.96757115178 * (BLY + 517.35106259548) / (BLY - 332.4503541985)) / 2;
//  double dRight = (-32.029669883833 * (TRY + 517.35106259548) / (TRY - 332.4503541985) - 23.96757115178 * (BRY + 517.35106259548) / (BRY - 332.4503541985)) / 2;
//  double dBL = dHBottom * (BLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(BLY - cameraHeight / 2));
//  double dBR = dHBottom * (BRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(BRY - cameraHeight / 2));
//  double dTL = dHTop * (TLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(TLY - cameraHeight / 2));
//  double dTR = dHTop * (TRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(TRY - cameraHeight / 2));
//  double dL = (dBL + dTL) / 2;
//  double dR = (dBR + dTR) / 2;
//  double goalLeftRelativeX = (dR * dR - dL * dL - goalWidth * goalWidth) / (2 * goalWidth);
//  double goalRightRelativeX = goalLeftRelativeX + goalWidth;
//  double goalRelativeY = dL * Math.sin(Math.acos((dR * dR - dL * dL - goalWidth * goalWidth) / 2 / dL / goalWidth));
	
	
//    public static double[] goalDistances(double TLX, double TLY, double TRX, double TRY, double BLX, double BLY, double BRX, double BRY) {
//    	return new double[] {
//			(dHBottom * (BLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
//			/ (focalLength - Math.sqrt(3.0)*(BLY - cameraHeight / 2))
//			+ dHTop * (TLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
//			/ (focalLength - Math.sqrt(3.0)*(TLY - cameraHeight / 2)))/2,
//    		(dHBottom * (BRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
//    		/ (focalLength - Math.sqrt(3.0)*(BRY - cameraHeight / 2))
//    		+ dHTop * (TRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
//    		/ (focalLength - Math.sqrt(3.0)*(TRY - cameraHeight / 2)))/2
//    	};
//	}
	/**
	 * Gives ground distances (in inches) to the goal (top left, bottom left, top right, bottom right) from image data (in pixels)
	 * @param TLY	y coordinate (in pixels) of the top left hand coordinate of the retroreflective tape around the goal
	 * @param TRY	y coordinate (in pixels) of the top right hand coordinate of the retroreflective tape around the goal
	 * @param BLY	y coordinate (in pixels) of the bottom left hand coordinate of the retroreflective tape around the goal
	 * @param BRY	y coordinate (in pixels) of the bottom right hand coordinate of the retroreflective tape around the goal
	 * @param ret	array in which distances (in inches) are returned: {top left, bottom left, top right, bottom right}
	 */
    public static void goalDistances(double TLY, double BLY, double TRY, double BRY, double[] ret) {
    	ret[0] = (focalLength * tanCameraMountAngle - TLY + cameraHeight / 2) / ((TLY - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    	ret[1] = (focalLength * tanCameraMountAngle - BLY + cameraHeight / 2) / ((BLY - cameraHeight / 2) * tanCameraMountAngle + dHBottom * focalLength);
    	ret[2] = (focalLength * tanCameraMountAngle - TRY + cameraHeight / 2) / ((TRY - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    	ret[3] = (focalLength * tanCameraMountAngle - BRY + cameraHeight / 2) / ((BRY - cameraHeight / 2) * tanCameraMountAngle + dHBottom * focalLength);
    	System.out.println("goal distance information:");
    	System.out.println("\tTLY, BLY, TRY, BRY: " + TLY + ", " + BLY + ", " + TRY + ", " + BRY);
    	System.out.println("\tdLeft, dRight: " + ((ret[0] + ret[1]) / 2) + ", " + ((ret[2] + ret[3]) / 2));
    }
    
    /**
     * Gives ground distances (in inches) to the goal (left, right) from image data
     * @param tl				is the top left pixel of the bounding box around the goal true?
     * @param br				is the bottom right pixel of the bounding box around the goal true?
     * @param boundBoxTopY		position (in pixels) of the top of the bounding box around the goal. This is smaller than boundBoxBottomY would be.
     * @param boundBoxHeight	height (in pixels) of the bounding box
     * @param ret				array in which distances (in inches) are returned: {left, right}
     */
    public static void goalDistances(boolean tl, boolean br, double boundBoxTopY, double boundBoxHeight, double[] ret) {
    	//ret[0] = ground distance to left side of goal
    	//ret[1] = ground distance to right side of goal
    	System.out.println("goal distance information");
    	if(tl || br) {
    		ret[0] = (focalLength * tanCameraMountAngle - boundBoxTopY + cameraHeight / 2) / 
    				((boundBoxTopY - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    		ret[1] = (focalLength * tanCameraMountAngle - boundBoxTopY - boundBoxHeight + cameraHeight / 2) /
    				((boundBoxTopY + cameraHeight - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    		System.out.println("\ttl/br");
    	} else {
    		ret[0] = (focalLength * tanCameraMountAngle - boundBoxTopY - boundBoxHeight + cameraHeight / 2) /
    				((boundBoxTopY + cameraHeight - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    		ret[1] = (focalLength * tanCameraMountAngle - boundBoxTopY + cameraHeight / 2) / 
    				((boundBoxTopY - cameraHeight / 2) * tanCameraMountAngle + dHTop * focalLength);
    		System.out.println("\ttr/bl");
    	}
		System.out.println("\tdLeft, dRight: " + ret[0] + ", " + ret[1]);
    }

    public static double getSkewAngle(double centerX, double viewAngle, int imageWidth) {
		double degreesPerPixel = viewAngle / imageWidth;
		return ((imageWidth / 2) - centerX) * degreesPerPixel;
    }
    
    /**
     * Gets the angle of the robot from the line parallel to the goal such that:
     * <br>
     * -When the robot is turned perpendicular to the goal, with the goal at its left, the return angle is 0 radians
     * <br>
     * -When the robot is turned perpendicular to the goal, with the goal at its right, the return angle is pi radians
     * <br>
     * -When the robot is pointed directly at the goal, the return angle is pi/2 radians
     * <br>
     * Takes dLeft and dRight as arguments rather than getting them from goalDistances, because they may already be calculated.
     * @param dLeft				ground distance (in inches) to the left hand side of the goal
     * @param dRight			ground distance (in inches) to the right hand side of the goal
     * @param xRHS_BoundingBox	position (in pixels) of the right hand side of the bounding box around the goal
     * @param degrees			if true, multiplies return by 180 / pi
     * @return
     */
    public static double getAngleToGoal(double dLeft, double dRight, double xRHS_BoundingBox, boolean degrees) {
    	double ret = ((degrees) ? (180 / Math.PI) : (1)) * (Math.acos((goalWidth * goalWidth + dRight * dRight - dLeft * dLeft) / (2 * goalWidth * dRight))
    			+ Math.acos((dLeft * dLeft + dRight * dRight - goalWidth * goalWidth) / (2 * dLeft * dRight))
    			+ Math.atan((cameraWidth / 2 - xRHS_BoundingBox) / focalLength));
    	System.out.println("angle to goal information");
    	System.out.println("\txRHS_BoundingBox, degrees: " + xRHS_BoundingBox + ", " + degrees);
    	System.out.println("\tangle: " + ret);
//    	return ((degrees) ? (180 / Math.PI) : (1)) * (Math.acos((goalWidth * goalWidth + dRight * dRight - dLeft * dLeft) / (2 * goalWidth * dRight))
//    			+ Math.acos((dLeft * dLeft + dRight * dRight - goalWidth * goalWidth) / (2 * dLeft * dRight))
//    			+ Math.atan((cameraWidth / 2 - xRHS_BoundingBox) / focalLength));
    	return ret;
    }
    /**
     * Returns the angle towards which the robot should turn in order to face the center of the goal.
     * Usage: getAngleToGoal() is process variable in a PID, while this is the setpoint.
     * 
     * @param dLeft		ground distance to the left side of the goal
     * @param dRight	ground distance to the right side of the goal
     * @param degrees	if true, multiplies return by 180 / pi
     * @return			angle towards which the robot should turn in order to face the center of the goal.
     */
    public static double angleToGoalSetpoint(double dLeft, double dRight, boolean degrees) {
    	double temp = (goalWidth * goalWidth + dRight * dRight - dLeft * dLeft) / 2 * goalWidth * dRight;
    	temp *= temp;
    	temp = Math.sqrt(1-temp);
    	double ret = ((degrees) ? (180 / Math.PI) : (1)) * Math.atan((goalWidth - (dLeft * dLeft + goalWidth * goalWidth - dRight * dRight) / goalWidth) / dRight * temp);
    	System.out.println("angle to goal setpoint information");
    	System.out.println("\tdegrees: " + degrees);
    	System.out.println("\tangle: " + ret);
//    	return ((degrees) ? (180 / Math.PI) : (1)) * Math.atan((goalWidth - (dLeft * dLeft + goalWidth * goalWidth - dRight * dRight) / goalWidth) / dRight * temp);
    	return ret;
    }
    
    /**
     * Gets angle that the robot should turn in order to face the center of the goal.
     * @param xlhsGoalBBox		x coordinate of the left hand side of the bounding box around the goal (in pixels)
     * @param widthGoalBBox		width of the bounding box around the goal (in pixels)
     * @param degrees			if true, returns degrees, if false, returns radians.
     * @return					angle to turn.
     */
    public static double turnAngle(double xlhsGoalBBox, double widthGoalBBox, boolean degrees) {
//    	return ((degrees) ? (180 / Math.PI) : (1)) * Math.atan((2 * xlhsGoalBBox + widthGoalBBox + 2 * cameraWidth)*Math.tan(viewAngleHoriz / 2) / cameraWidth);
    	double x = xlhsGoalBBox + widthGoalBBox / 2 - cameraWidth / 2;
    	return -Math.atan(2 * x * Math.tan(viewAngleHoriz / 2) / cameraWidth);
    }
}
