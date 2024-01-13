package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireAllSidesCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();

    public AcquireAllSidesCommand() {
        addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.runFront();
    }

    @Override
    public boolean isFinished() {
        // TODO: update with sensors
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopFront();
        acquisition.stopRear();
    }
}
