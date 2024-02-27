package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Storage;

public class AcquisitionRunCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();

    public AcquisitionRunCommand() {
        addRequirements(storage, acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquire();
        acquisition.runSushi();
    }

    @Override
    public boolean isFinished() {
        return acquisition.isNotePresent();
    }

    @Override
    public void end(boolean isFinished) {
        acquisition.stopSushi();
        acquisition.stopIntake();
    }
}
