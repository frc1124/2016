package org.usfirst.frc.team1124.robot;

import org.usfirst.frc.team1124.robot.commands.drive.AimTowardsGoalPID;
import org.usfirst.frc.team1124.robot.commands.drive.ArcadeDriveJoystick;
import org.usfirst.frc.team1124.robot.commands.drive.DriveHoldPosition;
import org.usfirst.frc.team1124.robot.commands.macro.IntakeBall;
import org.usfirst.frc.team1124.robot.commands.macro.RampAndIntakeInterrupt;
import org.usfirst.frc.team1124.robot.commands.macro.ScoreHighGoal;
import org.usfirst.frc.team1124.robot.commands.macro.ScoreLowGoal;
import org.usfirst.frc.team1124.robot.commands.oi.JS2ManualToggle;
import org.usfirst.frc.team1124.robot.commands.oi.JS3ManualToggle;
import org.usfirst.frc.team1124.robot.enums.JoystickManualState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	// joysticks
	
	private final Joystick js1 = new Joystick(0);
	private final Joystick js2 = new Joystick(1);
	private final Joystick js3 = new Joystick(2);
	
	// joystick buttons, 1 corresponds to 1, there is no button 0, don't try it, its null
	
	private final Button[] js1_buttons = { 
			null,
			new JoystickButton(js1, 1),
			new JoystickButton(js1, 2),
			new JoystickButton(js1, 3),
			new JoystickButton(js1, 4),
			new JoystickButton(js1, 5),
			new JoystickButton(js1, 6),
			new JoystickButton(js1, 7),
			new JoystickButton(js1, 8),
			new JoystickButton(js1, 9),
			new JoystickButton(js1, 10),
			new JoystickButton(js1, 11),
			new JoystickButton(js1, 12),
	   };
	
	private final Button[] js2_buttons = { 
			null,
			new JoystickButton(js2, 1),
			new JoystickButton(js2, 2),
			new JoystickButton(js2, 3),
			new JoystickButton(js2, 4),
			new JoystickButton(js2, 5),
			new JoystickButton(js2, 6),
			new JoystickButton(js2, 7),
			new JoystickButton(js2, 8),
			new JoystickButton(js2, 9),
			new JoystickButton(js2, 10),
			new JoystickButton(js2, 11),
			new JoystickButton(js2, 12),
	   };
	
	private final Button[] js3_buttons = { 
			null,
			new JoystickButton(js3, 1),
			new JoystickButton(js3, 2),
			new JoystickButton(js3, 3),
			new JoystickButton(js3, 4),
			new JoystickButton(js3, 5),
			new JoystickButton(js3, 6),
			new JoystickButton(js3, 7),
			new JoystickButton(js3, 8),
			new JoystickButton(js3, 9),
			new JoystickButton(js3, 10),
			new JoystickButton(js3, 11),
			new JoystickButton(js3, 12),
	   };
	
	private JoystickManualState js2_manual = JoystickManualState.None;
	private JoystickManualState js3_manual = JoystickManualState.None;
	
	public OI(){
		// Driver 1, Joystick 1
		
		js1_buttons[7].whenPressed(new ArcadeDriveJoystick());
		js1_buttons[8].whenPressed(new DriveHoldPosition(true));

		js1_buttons[1].whenPressed(new ScoreHighGoal());
		js1_buttons[3].whenPressed(new AimTowardsGoalPID()); // this is a manual fallback control
		
		// Driver 2, Joystick 2
		
		js2_buttons[9].whenPressed(new IntakeBall());
		js2_buttons[11].whenPressed(new RampAndIntakeInterrupt());
		
		js2_buttons[10].whenPressed(new ScoreLowGoal());
		js2_buttons[12].whenPressed(new RampAndIntakeInterrupt());

		js2_buttons[3].whenPressed(new JS2ManualToggle(3));
		js2_buttons[4].whenPressed(new JS2ManualToggle(4));
		
		js2_buttons[5].whenPressed(new JS2ManualToggle(5));
		js2_buttons[6].whenPressed(new JS2ManualToggle(6));
		
		// Driver 2, Joystick 3
		
		js3_buttons[3].whenPressed(new JS3ManualToggle(3));
		
		js3_buttons[5].whenPressed(new JS3ManualToggle(5));
	}
	
	// Joystick gets (used for getting axes)
	
	public Joystick getJS1(){
		return js1;
	}
	
	public Joystick getJS2(){
		return js2;
	}
	
	public Joystick getJS3(){
		return js3;
	}
	
	// Manual control toggles
	
	public JoystickManualState getJS2ManState(){
		return js2_manual;
	}
	
	public JoystickManualState getJS3ManState(){
		return js3_manual;
	}
	
	public void setJS2ManState(JoystickManualState state){
		js2_manual = state;
	}
	
	public void setJS3ManState(JoystickManualState state){
		js3_manual = state;
	}
}

