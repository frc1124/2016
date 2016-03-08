package org.usfirst.frc.team1124.robot.tools.vision;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import org.usfirst.frc.team1124.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ParticleFilterCriteria2;
import com.ni.vision.NIVision.ParticleFilterOptions2;
import com.ni.vision.NIVision.Range;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * An attempt at running vision local to the RoboRIO. This class will take an image from a camera at the time of
 * instantiation. If no camera is provided, it will use the shooter camera. Then you only need to call getDistance(),
 * getAngle(), or getCenterOfTarget(). It caches results so it will not duplicate effort.
 * 
 * If you want to update the image, call updateImage(). It will take a new image with the last camera provided. 
 * You can optionally pass a USB or Axis camera to updateImage() and it will switch to using that camera. The next
 * call to getDistance(), getAngle(), or getCenterOfTarget() will trigger the analysis.
 * 
 */
public class ImageAnalysis {
	// Camera used
	private AxisCamera axisCamera = null;
	private USBCamera usbCamera = null;

	// Camera image
	private Image image = null;

	// Analyzed image
	private Image targetImage = null;

	// Target values
	private static double PARTICLE_AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	private static double AXIS_VIEW_ANGLE = 47; //49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	private static double USB_VIEW_ANGLE = 51;

	// Output
	private Double angle = null;
	private Double distance = null;
	private Integer centerOfTarget = null;

	public ImageAnalysis(String filename) {
		// Read in image
		this.image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		NIVision.imaqReadFile(this.image,filename);
	}

	public ImageAnalysis() {
		this.axisCamera = Robot.shooter_camera;
		this.getImageFromAxisCamera();
	}

	public ImageAnalysis(Image image) {
		this.image = image;
	}

	public ImageAnalysis(AxisCamera camera) {
		this.axisCamera = camera;
		this.getImageFromAxisCamera();
	}

	public ImageAnalysis(USBCamera camera) {
		this.usbCamera = camera;
	}

	private void getImageFromAxisCamera() {
		// Get a frame from the axis camera
		if (this.axisCamera == null) {
			return;
		}
	    this.image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		if (!this.axisCamera.getImage(this.image)) {
			this.image = null;
		}
	}

	private void getImageFromUsbCamera() {
		// Get a frame from the axis camera
		if (this.usbCamera == null) {
			return;
		}
	    this.image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	    this.usbCamera.getImage(this.image);
	}

	public void updateImage() {
		// Clear data
		this.image = null;
		this.targetImage = null;
		this.angle = null;
		this.distance = null;
		this.centerOfTarget = null;

		// Update from the last given camera
		if (this.axisCamera != null) {
			this.getImageFromAxisCamera();
		} else if (this.usbCamera != null) {
			this.getImageFromUsbCamera();
		}
	}

	public void updateImage(AxisCamera camera) {
		this.axisCamera = camera;
		this.usbCamera = null;
	}

	public void updateImage(USBCamera camera) {
		this.usbCamera = camera;
		this.axisCamera = null;
	}

	/**
	 * Process the image for finding convex hulls.
	 */
	private void processImage() {
		// Create a temporary buffer for processing
		Image buffer = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);

		// IMAQ ColorThreshold(HSL,1,80/120,120/255,40/100)
		NIVision.imaqColorThreshold(buffer,this.image,1,NIVision.ColorMode.HSL,new Range(80,120),new Range(120,255),new Range(40,100));

		// IMAQ RemoveParticle(true,false,1,false) by filtering out particles
		ParticleFilterCriteria2[] criteria = new ParticleFilterCriteria2[1];
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, ImageAnalysis.PARTICLE_AREA_MINIMUM, 100, 0, 0);
		ParticleFilterOptions2 filterOptions = new ParticleFilterOptions2(1, 0, 1, 0);
		NIVision.imaqParticleFilter4(buffer, buffer, criteria, filterOptions, null);

		// IMAQ ConvexHull(true)
		this.targetImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		NIVision.imaqConvexHull(targetImage,buffer,1);
	}

	/**
	 * If the image has not been analyzed, process it. Then analyze the target image.
	 */
	private void analyzeTargetImage() {
		if (this.targetImage == null) {
			this.processImage();
		}

		// Report the target found, if any
		int numParticles = NIVision.imaqCountParticles(this.targetImage, 1);
		if (numParticles == 0) {
			return;
		}

		// Measure particles and sort by particle size
		Vector<ParticleReport> particles = new Vector<ParticleReport>();
		for(int particleIndex = 0; particleIndex <numParticles; particleIndex++) {
			ParticleReport par = new ParticleReport();
			par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
			par.Area = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
			par.ConvexHullArea = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
			par.BoundingRectTop = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			par.BoundingRectLeft = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			par.BoundingRectBottom = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			par.BoundingRectRight = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			par.CenterOfMassX = NIVision.imaqMeasureParticle(this.targetImage, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			particles.add(par);
		}
		Collections.sort(particles);

		// Find center of target of the largest target
		ParticleReport p = particles.elementAt(0);
		this.centerOfTarget = (int)(p.BoundingRectRight - p.BoundingRectLeft)/2;

		// TO DO: Find the corners of the goal or find if topLeft/bottomRight pixels are on or off
		// TO DO: use VisionTools.goalDistances to get distance

		// Find the angle to the center of the goal
		int imageWidth = NIVision.imaqGetImageSize(this.image).width;
		this.angle = VisionTools.getSkewAngle(p.CenterOfMassX,(this.axisCamera != null) ? ImageAnalysis.AXIS_VIEW_ANGLE : ImageAnalysis.USB_VIEW_ANGLE, imageWidth);
	}

	/**
	 * Returns the angle to turn based on the vision analysis.
	 * 
	 * @return angle in degrees
	 */
	public double getAngle() {
		// Figure out how far to turn
		if (this.angle == null) {
			this.analyzeTargetImage();
		}
		return this.angle;
	}

	/**
	 * Returns the center of the target.
	 * @return	center X of target in image
	 */
	public double getCenterOfTarget() {
		// Figure out how far to turn
		if (this.centerOfTarget == null) {
			this.analyzeTargetImage();
		}
		return this.centerOfTarget;
	}

	/**
	 * Returns the calculated distance based on vision analysis.
	 * @return	distance in inches
	 */
	public double getDistance() {
		// Figure out how far away we are based on analysis
		if (this.distance == null) {
			this.analyzeTargetImage();
		}
		return this.distance;
	}

	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		public double CenterOfMassX;
		double PercentAreaToImageArea;
		double Area;
		double ConvexHullArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	};
}
