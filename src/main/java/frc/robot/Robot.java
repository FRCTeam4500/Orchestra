package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.littletonrobotics.junction.LoggedRobot;

public class Robot extends LoggedRobot {
	@Override
	public void robotInit() {
		new RobotContainer();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}
}
