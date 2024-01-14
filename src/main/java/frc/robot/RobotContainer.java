package frc.robot;

import java.io.FilenameFilter;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.hardware.TalonFX;

import java.io.File;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    private CommandXboxController xbox;
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
        
        Orchestra orchestra = new Orchestra();
        for (int id : motorIDs) {
            orchestra.addInstrument(new TalonFX(id));
        }

        xbox.a().onTrue(Commands.runOnce(
                () -> orchestra.loadMusic(songSelector.getSelected())    
            ).andThen(
                Commands.waitSeconds(0.25)
            ).andThen(
                Commands.runOnce(() -> orchestra.play())
            )  
        );

        xbox.y().onTrue(Commands.runOnce(
            () -> orchestra.stop()      
        ));
    } 
}
