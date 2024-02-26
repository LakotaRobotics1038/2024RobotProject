package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftDownCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftDownCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runDown();
    }

    @Override
    public boolean isFinished() {
        return lift.bothLowerLimitsReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.stop();
    }
}
