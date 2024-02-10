package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Storage;

public class FullAcquireSequenceCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();

    public FullAcquireSequenceCommand() {
        addRequirements(storage, acquisition);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        acquisition.acquire();
        acquisition.runSushi();
        storage.runTransition();
        storage.runStorage();
    }

    @Override
    public boolean isFinished() {
        if (storage.noteExitingStorage()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean isFinished) {
        storage.stopStorage();
        storage.stopTransition();
        acquisition.stopIntake();
        acquisition.stopSushi();
    }
}
