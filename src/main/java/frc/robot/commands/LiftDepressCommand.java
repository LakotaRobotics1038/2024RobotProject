package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftDepressCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftDepressCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        Lift.runNeg();
    }

    @Override
    public boolean isFinished() {
        return false;
        // awaiting sensors
    }

    @Override
    public void end(boolean interrupted) {
        Lift.stop();
    }

}