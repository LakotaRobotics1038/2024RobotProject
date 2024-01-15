package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();

    public AcquireCommand() {
        addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquire();
    }

    @Override
    public boolean isFinished() {
        // TODO: update with sensors
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stop();
    }
}
