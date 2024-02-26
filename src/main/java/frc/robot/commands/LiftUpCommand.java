package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftUpCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftUpCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.disableRatchets();
    }

    @Override
    public void execute() {
        lift.runUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lift.stop();
        lift.enableRatchets();
    }
}
