package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class OrchestraCommand extends CommandBase {
    private Orchestra mOrchestra;
    private int loadingSongDelay;
    private boolean hasQueue;

    public OrchestraCommand(int... ids) {
        mOrchestra = new Orchestra();
        for (int id : ids) {
            mOrchestra.addInstrument(new TalonFX(id));
        }
    }

    public void changeSong(String filePath) {
        mOrchestra.loadMusic(filePath);
        loadingSongDelay = 10;
        hasQueue = true;
    }

    public void stop() {
        hasQueue = false;
        mOrchestra.stop();
    }

    @Override
    public void initialize() {
        loadingSongDelay = 0;
        hasQueue = false;
    }

    @Override
    public void execute() {
        if (hasQueue) {
            if (loadingSongDelay > 0) {
                loadingSongDelay--;
            } else {
                mOrchestra.play();
                hasQueue = false;
            }
        }
    }
}
