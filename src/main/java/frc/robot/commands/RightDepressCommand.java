package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class RightDepressCommand extends Command {
    private Lift lift = Lift.getInstance();

    public RightDepressCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runRightNeg();
    }

    @Override
    public boolean isFinished() {
        return false;
        // awaiting sensors
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopRightMotor();
    }
}
