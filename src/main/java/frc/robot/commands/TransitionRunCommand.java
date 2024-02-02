package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Storage;

public class TransitionRunCommand extends Command {
    private Storage storage = Storage.getInstance();

    private double speed;

    public TransitionRunCommand() {
        addRequirements(storage);
    }

    @Override
    public void execute() {
        storage.runTransition();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        storage.stopTransition();
    }

}
