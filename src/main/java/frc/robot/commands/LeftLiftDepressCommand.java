package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LeftLiftDepressCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LeftLiftDepressCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runLeftNeg();
    }

    @Override
    public boolean isFinished() {
        return false;
        // awaiting sensors
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopLeftMotor();
    }
}
