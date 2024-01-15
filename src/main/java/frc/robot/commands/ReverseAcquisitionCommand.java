package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.constants.AcquisitionConstants;

public class ReverseAcquisitionCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();

    public ReverseAcquisitionCommand() {
        addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquire(AcquisitionConstants.spitOutMotorSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stop();
    }
}