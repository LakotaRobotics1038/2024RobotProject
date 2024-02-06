package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class SushiForwardCommand extends Command {
    private static Acquisition acquisition = Acquisition.getInstance();

    public SushiForwardCommand() {
        addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.runSushi();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopIntake();
        acquisition.stopSushi();
    }
}
