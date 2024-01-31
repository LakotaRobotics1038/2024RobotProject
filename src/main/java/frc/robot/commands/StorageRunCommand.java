package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class StorageRunCommand extends Command {
    private Storage storage = Storage.getInstance();

    private double speed;

    public StorageRunCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        storage.run();
    }

    @Override
    public boolean isFinished() {
        return storage.getLaserOutput();
    }

    @Override
    public void end(boolean interrupted) {
        storage.stop();
    }

}
