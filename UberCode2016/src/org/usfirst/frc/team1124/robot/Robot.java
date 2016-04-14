package org.usfirst.frc.team1124.robot;

// commands
import org.usfirst.frc.team1124.robot.commands.macro.Autonomous;

// subsystems
import org.usfirst.frc.team1124.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1124.robot.subsystems.ArmIntakeWheels;
import org.usfirst.frc.team1124.robot.subsystems.RampBelts;
import org.usfirst.frc.team1124.robot.subsystems.ShooterPID;
import org.usfirst.frc.team1124.robot.subsystems.ArmActuator;
import org.usfirst.frc.team1124.robot.subsystems.ArmPistons;
import org.usfirst.frc.team1124.robot.subsystems.Camera;
import edu.wpi.first.wpilibj.Compressor;

// cameras
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.USBCamera;

// tools
import org.usfirst.frc.team1124.robot.tools.ConfigIO;

// dashboard
import org.usfirst.frc.team1124.robot.dashboard.DashboardConnection;
import org.usfirst.frc.team1124.robot.dashboard.SafetyErrorLogger;

// wpilib components
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * I'm never gonna dance again
 * Guilty feet have got no rhythm
 * Though it's easy to pretend
 * I know your not a fool
 * 
 * Should've known better than to cheat a friend
 * And waste the chance that I've been given
 * So I'm never gonna dance again
 * The way I danced with you~
 */
public class Robot extends IterativeRobot {
	// operator interface
	public static OI oi;
	
	// configuration interface
	public static ConfigIO configIO;
	
	// subsystems
	public static DriveTrain drivetrain;
	public static ArmPistons arm_pistons;
	public static ArmActuator arm_actuator;
	public static RampBelts ramp_belts;
	public static ArmIntakeWheels arm_intake_wheels;
	public static ShooterPID shooter_pid;
	
	public static USBCamera intake_camera;
	public static AxisCamera shooter_camera;
	
	// components
	public static PowerDistributionPanel pdp;
	public static Compressor compressor;
	
	// dash board and camera
	public static DashboardConnection dashboard = new DashboardConnection();
	public static Camera camera;
	
	// autonomous
    Command autonomousCommand;
    
    // shooter priming
    Command bringShooterToSpeed;
    
    // this revision of code (displayed on dash board)
    
    public static String codeRevision = "[v7.0.2]:week_7:light_sensors";

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// instantiate configuration interface
    	configIO = new ConfigIO();
    	
    	// instantiate subsystems
		drivetrain = new DriveTrain();
		pdp = new PowerDistributionPanel();
		compressor = new Compressor();
		arm_pistons = new ArmPistons();
		arm_actuator = new ArmActuator();
		ramp_belts = new RampBelts();
		arm_intake_wheels = new ArmIntakeWheels();
		shooter_pid = new ShooterPID();
		camera = new Camera();
		
		// instantiate operator interface
		oi = new OI();
        
        // set up error logger
        SafetyErrorLogger.init();
        
        // set up camera control
        camera.setHeld(false);

        // instantiate the command used for the autonomous period
        autonomousCommand = new Autonomous();
        
        // start the compressor
        compressor.start();
        
        // init pistons
        arm_pistons.retract();
    }
	
	public void disabledPeriodic() {
    	dashboard.updateDashboard();
    	
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command
    	drivetrain.setBrake();
    	
        if(autonomousCommand != null){
        	autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	dashboard.updateDashboard();
    	
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	drivetrain.setCoast();
    	
        if(autonomousCommand != null){
        	autonomousCommand.cancel();
        }
        
        bringShooterToSpeed.start();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	drivetrain.setBrake();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	dashboard.updateDashboard();
    	
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
