package org.usfirst.frc.team1124.robot.commands.arm;

import org.usfirst.frc.team1124.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

//_____  _____  ______   ________  _______      _______  _____   ______   _________    ___   ____  _____   ______   
//|_   _||_   _||_   _ \ |_   __  ||_   __ \    |_   __ \|_   _|.' ____ \ |  _   _  | .'   `.|_   \|_   _|.' ____ \  
//| |    | |    | |_) |  | |_ \_|  | |__) |     | |__) | | |  | (___ \_||_/ | | \_|/  .-.  \ |   \ | |  | (___ \_| 
//| '    ' |    |  __'.  |  _| _   |  __ /      |  ___/  | |   _.____`.     | |    | |   | | | |\ \| |   _.____`.  
//\ \__/ /    _| |__) |_| |__/ | _| |  \ \_   _| |_    _| |_ | \____) |   _| |_   \  `-'  /_| |_\   |_ | \____) | 
// `.__.'    |_______/|________||____| |___| |_____|  |_____| \______.'  |_____|   `.___.'|_____|\____| \______.' 
//      
public class ArmPistonsRetract extends Command {

    public ArmPistonsRetract() {
        requires(Robot.arm_pistons);
        setInterruptible(true);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.arm_pistons.retract();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
