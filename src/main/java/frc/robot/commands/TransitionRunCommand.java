package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.StorageConstants;
import frc.robot.subsystems.Storage;

public class TransitionRunCommand extends Command {
    private Storage storage = Storage.getInstance();

    private Timer timer = new Timer();
    private double timeToRun = StorageConstants.defaultTimeToRun;

    public TransitionRunCommand() {
        addRequirements(storage);
    }

    public TransitionRunCommand(double timeToRun) {
        addRequirements(storage);
        this.timeToRun = timeToRun;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        storage.runTransition();
    }

    @Override
    public boolean isFinished() {
        return (timeToRun <= 0) || (timer.get() > timeToRun) || storage.noteEnteringStorage();
    }

    @Override
    public void end(boolean interrupted) {
        storage.stopTransition();
        timer.stop();
        timer.reset();
    }

}
