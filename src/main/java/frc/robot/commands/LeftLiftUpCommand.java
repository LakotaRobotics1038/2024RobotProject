package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LeftLiftUpCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LeftLiftUpCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.disableLeftRatchet();
    }

    @Override
    public void execute() {
        lift.runLeftUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopLeftMotor();
        lift.enableLeftRatchet();
    }
}
