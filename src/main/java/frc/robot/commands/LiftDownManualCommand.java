package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftDownManualCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftDownManualCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runLeftDown();
        lift.runRightDown();
    }

    @Override
    public boolean isFinished() {
        return lift.leftLowerLimitReached() && lift.rightLowerLimitReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopMotors();
    }
}
