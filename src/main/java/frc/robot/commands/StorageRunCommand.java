package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class StorageRunCommand extends Command {
    private Storage storage = Storage.getInstance();

    private double speed;

    public StorageRunCommand(double speed) {
        addRequirements(storage);
        this.speed = speed;
    }

    @Override
    public void execute() {
        storage.run(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        storage.stop();
    }

}
