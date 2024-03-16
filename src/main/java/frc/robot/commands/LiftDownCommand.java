package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.LiftConstants;
import frc.robot.subsystems.Lift;

public class LiftDownCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftDownCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.enable();
    }

    @Override
    public void execute() {
        lift.setSetpoint(LiftConstants.minLiftInches);
    }

    @Override
    public boolean isFinished() {
        return lift.leftLowerLimitReached() && lift.rightLowerLimitReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.disable();
    }
}
