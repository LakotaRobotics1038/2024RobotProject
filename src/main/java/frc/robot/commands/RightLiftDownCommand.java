package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class RightLiftDownCommand extends Command {
    private Lift lift = Lift.getInstance();

    public RightLiftDownCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runRightDown();
    }

    @Override
    public boolean isFinished() {
        return lift.rightLowerLimitReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopRightMotor();
    }
}
