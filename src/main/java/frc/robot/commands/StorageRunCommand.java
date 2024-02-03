package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class StorageRunCommand extends Command {
    private Storage storage = Storage.getInstance();

    private boolean seenNote;
    private double speed;

    public StorageRunCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        storage.runStorage();
        storage.runTransition();
    }

    @Override
    public boolean isFinished() {
        if (storage.noteExitingStorage()) {
            seenNote = true;
            return false;
        } else if (!storage.noteEnteringStorage() && seenNote) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        storage.stopStorage();
        storage.stopTransition();
    }

}