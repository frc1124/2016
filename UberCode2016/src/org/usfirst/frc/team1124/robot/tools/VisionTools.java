package org.usfirst.frc.team1124.robot.tools;

public class VisionTools {
	private static final double cameraWidth = 320;
	private static final double cameraHeight = 240;
	private static final double viewAngleHoriz = 47 * Math.PI / 180;
	private static final double focalLength = cameraWidth / 2 / Math.tan(viewAngleHoriz / 2);
	private static final double goalWidth = 20;   //actual--I've just forgotten the number
	private static final double goalBottomHeight = 83;
	private static final double goalTopHeight = 97;
	private static final double cameraMountHeight = 41.523;
	private static final double dHBottom = goalBottomHeight - cameraMountHeight;
	private static final double dHTop = goalTopHeight - cameraMountHeight;
//	private static final double cameraCoRdist = #;
//	double TLX;
//	double TLY;
//	double BLX;
//	double BLY;
//    double TRX;
//    double TRY;
//    double BRX;
//    double BRY;
//    double dLeft = (-32.029669883833 * (TLY + 517.35106259548) / (TLY - 332.4503541985) - 23.96757115178 * (BLY + 517.35106259548) / (BLY - 332.4503541985)) / 2;
//    double dRight = (-32.029669883833 * (TRY + 517.35106259548) / (TRY - 332.4503541985) - 23.96757115178 * (BRY + 517.35106259548) / (BRY - 332.4503541985)) / 2;
//    double dBL = dHBottom * (BLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(BLY - cameraHeight / 2));
//    double dBR = dHBottom * (BRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(BRY - cameraHeight / 2));
//    double dTL = dHTop * (TLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(TLY - cameraHeight / 2));
//    double dTR = dHTop * (TRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))/(focalLength - Math.sqrt(3.0)*(TRY - cameraHeight / 2));
//    double dL = (dBL + dTL) / 2;
//    double dR = (dBR + dTR) / 2;
//    double goalLeftRelativeX = (dR * dR - dL * dL - goalWidth * goalWidth) / (2 * goalWidth);
//    double goalRightRelativeX = goalLeftRelativeX + goalWidth;
//    double goalRelativeY = dL * Math.sin(Math.acos((dR * dR - dL * dL - goalWidth * goalWidth) / 2 / dL / goalWidth));
    
	
	/**
	 * Gives distances to the goal (left and right) from image data
	 * @param TLX	x coordinate of the top left hand coordinate of the retroreflective tape around the goal
	 * @param TLY	y coordinate of the top left hand coordinate of the retroreflective tape around the goal
	 * @param TRX	x coordinate of the top right hand coordinate of the retroreflective tape around the goal
	 * @param TRY	y coordinate of the top right hand coordinate of the retroreflective tape around the goal
	 * @param BLX	x coordinate of the bottom left hand coordinate of the retroreflective tape around the goal
	 * @param BLY	y coordinate of the bottom left hand coordinate of the retroreflective tape around the goal
	 * @param BRX	x coordinate of the bottom right hand coordinate of the retroreflective tape around the goal
	 * @param BRY	y coordinate of the bottom right hand coordinate of the retroreflective tape around the goal
	 * @return	{ground distance to left hand side of goal, ground distance to right hand side of goal}
	 */
    double[] goalDistances(double TLX, double TLY, double TRX, double TRY, double BLX, double BLY, double BRX, double BRY) {
    	return new double[] {
			(dHBottom * (BLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
			/ (focalLength - Math.sqrt(3.0)*(BLY - cameraHeight / 2))
			+ dHTop * (TLY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
			/ (focalLength - Math.sqrt(3.0)*(TLY - cameraHeight / 2)))/2,
    		(dHBottom * (BRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
    		/ (focalLength - Math.sqrt(3.0)*(BRY - cameraHeight / 2))
    		+ dHTop * (TRY - cameraHeight / 2 + focalLength * Math.sqrt(3.0))
    		/ (focalLength - Math.sqrt(3.0)*(TRY - cameraHeight / 2)))/2
    	};
    }
}
