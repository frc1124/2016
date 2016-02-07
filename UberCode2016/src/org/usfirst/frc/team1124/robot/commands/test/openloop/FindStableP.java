package org.usfirst.frc.team1124.robot.commands.test.openloop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import edu.wpi.first.wpilibj.command.CommandGroup;

@SuppressWarnings({"rawtypes","unchecked"})
public class FindStableP extends CommandGroup {
	private int decExp = -5;
	private int factor = 1;
	private TryP testCommand = null;
	private Class classType = null;
	private boolean foundStableP = false;
	private double offset = 50.0;

	public FindStableP(Class c) {
		this.classType = c;
	}

	@Override
	protected void initialize() {
	}

	private TryP createTryPCommand(double offset,double p) {
		try {
			Constructor constructor = this.classType.getDeclaredConstructor(Double.class,Double.class);
			return (TryP)constructor.newInstance(offset,p);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void runTestForCurrentP() {
		this.testCommand = this.createTryPCommand(this.offset,this.getP());
		this.addSequential(this.testCommand);
	}

	public double getP() {
		return this.factor * Math.pow(10.0,this.decExp);
	}

	public double getI() {
		return this.testCommand.getI();
	}

	public double getD() {
		return this.testCommand.getD();
	}

	@Override
	protected void execute() {
		if (this.testCommand == null) {
			this.runTestForCurrentP();
			return;
		}
		if (!testCommand.isFinished()) {
			return;
		}

		// Run the command with different P values until we find the ultimate gain
		if (this.testCommand.hasStableP()) {
			// Update P
			if (this.factor < 3) {
				this.factor = (this.factor == 1) ? 2 : 5;
			} else {
				this.factor = 1;
				this.decExp++;
			}

			// Toggle the offset
			this.offset *= -1;

			// Run the command
			this.runTestForCurrentP();
			
			return;
		}
	}

	@Override
	protected boolean isFinished() {
		return this.foundStableP;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
