package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();
    private Timer timer = new Timer();
    private double timeToAcquire;
    private boolean seen = false;

    public AcquireCommand() {
        addRequirements(acquisition);
    }

    public AcquireCommand(double timeToAcquire) {
        addRequirements(acquisition);
        this.timeToAcquire = timeToAcquire;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        acquisition.acquire();
    }

    @Override
    public boolean isFinished() {
        // TODO: update with sensors
        if (acquisition.isNotePresent()) {
            seen = true;
        }
        if (timeToAcquire != 0) {
            return (seen && !acquisition.isNotePresent()) || (timeToAcquire == 0) || (timer.get() > timeToAcquire);
        } else {
            seen = false;
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopSushi();
        acquisition.stopSushi();
        timer.stop();
        timer.reset();
    }
}
