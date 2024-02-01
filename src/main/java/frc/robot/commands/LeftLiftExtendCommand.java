package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LeftLiftExtendCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LeftLiftExtendCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runLeftPos();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lift.stopLeftMotor();
    }

}
