package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class RightLiftExtendCommand extends Command {
    private Lift lift = Lift.getInstance();

    public RightLiftExtendCommand() {
        addRequirements(lift);
    }

    @Override
    public void execute() {
        lift.runRightPos();
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
