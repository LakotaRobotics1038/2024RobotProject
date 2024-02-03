package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.subsystems.Acquisition;

public class AcquireCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();
    private Timer timer = new Timer();
    private double timeToAcquire;

    public AcquireCommand() {
        addRequirements(acquisition);
        this.timeToAcquire = AcquisitionConstants.defaultTimeToAcquire;
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

        return acquisition.isNotePresent() || (timeToAcquire == 0) || (timer.get() > timeToAcquire);
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stop();
        timer.stop();
        timer.reset();
    }
}
