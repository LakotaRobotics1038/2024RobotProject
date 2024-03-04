package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Storage;

public class StorageRunCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();

    public StorageRunCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        storage.runStorage();
        storage.runTransition();
        acquisition.reverseSushi();
    }

    @Override
    public boolean isFinished() {
        return storage.noteExitingStorage();
    }

    @Override
    public void end(boolean interrupted) {
        storage.stopStorage();
        storage.stopTransition();
        acquisition.stopSushi();
    }

}