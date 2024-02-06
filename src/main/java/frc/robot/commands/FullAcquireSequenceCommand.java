package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.Acquisition;

public class FullAcquireSequenceCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();
    private boolean isFinished;

    public FullAcquireSequenceCommand() {
        addRequirements(storage, acquisition);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        isFinished = false;
        acquisition.acquire();

        if (acquisition.isNotePresent()) {
            storage.runTransition();
            acquisition.reverseMotors();

            if (storage.noteEnteringStorage()) {
                storage.stopTransition();
                storage.runStorage();

                if (storage.noteExitingStorage()) {
                    isFinished = true;
                }
            }

        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean isFinished) {
        storage.stopStorage();
        storage.stopTransition();
    }
}
