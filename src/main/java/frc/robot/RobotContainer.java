package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    private CommandXboxController xbox;
    private OrchestraCommand orchestraCommand;
    private SendableChooser<String> songSelector;

	public RobotContainer() {
        xbox = new CommandXboxController(2);
        setupSongSelection();
        setupOrchestra(2, 3, 4, 5, 6, 7, 8, 9);
    }

    public void setupOrchestra(int... motorIDs) {
        orchestraCommand = new OrchestraCommand(
            motorIDs
        );

        xbox.start().onTrue(orchestraCommand);

        xbox.a().onTrue(Commands.runOnce(
            () -> orchestraCommand.changeSong(songSelector.getSelected())
        ));

        xbox.y().onTrue(Commands.runOnce(
            () -> orchestraCommand.stop()
        ));
    }

    public void setupSongSelection() {
        songSelector = new SendableChooser<String>();
        songSelector.setDefaultOption("Output", "output.chrp");
        songSelector.addOption("Song 1", "song1.chrp");
        songSelector.addOption("Song 2", "song2.chrp");
        songSelector.addOption("Song 3", "song3.chrp");
        songSelector.addOption("Song 4", "song4.chrp");
        songSelector.addOption("Song 5", "song5.chrp");
        songSelector.addOption("Song 6", "song6.chrp");
        songSelector.addOption("Song 7", "song7.chrp");
        songSelector.addOption("Song 8", "song8.chrp");
        songSelector.addOption("Song 9", "song9.chrp");
        songSelector.addOption("Song 10", "song10.chrp");
        songSelector.addOption("Song 11", "song11.chrp");
        Shuffleboard.getTab("Song Selection Screen").add("Song Selector", songSelector);
    }
}
