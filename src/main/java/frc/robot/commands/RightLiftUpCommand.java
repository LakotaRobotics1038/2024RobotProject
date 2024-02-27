package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class RightLiftUpCommand extends Command {
    private Lift lift = Lift.getInstance();

    public RightLiftUpCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.disableRightRatchet();
    }

    @Override
    public void execute() {
        lift.runRightUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopRightMotor();
        lift.enableRightRatchet();
    }
}
