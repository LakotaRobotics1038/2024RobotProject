package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LeftLiftDownCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LeftLiftDownCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runLeftDown();
    }

    @Override
    public boolean isFinished() {
        return lift.leftLowerLimitReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopLeftMotor();
    }
}