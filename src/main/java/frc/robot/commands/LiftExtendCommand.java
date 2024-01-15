package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftExtendCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftExtendCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runPos();
    }

    @Override
    public boolean isFinished() {
        return false;
        // awaiting sensors
    }

    @Override
    public void end(boolean interrupted) {
        lift.stop();
    }

}
