package frc.robot;

import java.io.FilenameFilter;
import java.io.File;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    private CommandXboxController xbox;
    private OrchestraCommand orchestraCommand;
    private SendableChooser<String> songSelector;

	public RobotContainer() {
        setupSongSelection();
        setupOrchestra(2, 3, 4, 5, 6, 7, 8, 9);
    }
    
    private String[] getChrpFileNames() {
        return Filesystem.getDeployDirectory().list(
            new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".chrp");
                }
            }
        );
    }

    private void setupSongSelection() {
        songSelector = new SendableChooser<String>();
        songSelector.setDefaultOption("None", null);
        for (String fileName : getChrpFileNames()) {
            songSelector.addOption(
                fileName.split("\\.(?=[^\\.]+$)")[0], 
                fileName
            );
        }
        Shuffleboard.getTab("Song Selection Screen").add("Song Selector", songSelector);
    }

    private void setupOrchestra(int... motorIDs) {
        xbox = new CommandXboxController(2);
        orchestraCommand = new OrchestraCommand(motorIDs);

        xbox.start().onTrue(orchestraCommand);

        xbox.a().onTrue(Commands.runOnce(
            () -> orchestraCommand.changeSong(songSelector.getSelected())
        ));

        xbox.y().onTrue(Commands.runOnce(
            () -> orchestraCommand.stop()
        ));
    } 
}
