package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();

    private double speed;

    public AcquireCommand(double speed) {
        addRequirements(acquisition);
        this.speed = speed;
    }

    @Override
    public void execute() {
        acquisition.acquire(speed);
    }

    @Override
    public boolean isFinished() {
        return acquisition.getLaserOutput();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stop();
    }
}
