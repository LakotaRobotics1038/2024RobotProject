package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class ReverseStorageCommand extends Command {
    private Storage storage = Storage.getInstance();

    public ReverseStorageCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        storage.reverseTransition();
        storage.reverseStorage();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        storage.stopTransition();
        storage.stopStorage();
    }

}
