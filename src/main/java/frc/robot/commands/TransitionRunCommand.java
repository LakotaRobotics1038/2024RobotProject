package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Storage;

public class TransitionRunCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();

    public TransitionRunCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        acquisition.acquire();
        acquisition.reverseSushi();
        storage.runTransition();
    }

    @Override
    public boolean isFinished() {
        return storage.noteEnteringStorage();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopIntake();
        acquisition.stopSushi();
        storage.stopStorage();
    }

}
