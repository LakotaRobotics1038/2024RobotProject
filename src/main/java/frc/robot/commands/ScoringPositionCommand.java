package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;

public class ScoringPositionCommand extends Command {

    private Scoring scoring = Scoring.getInstance();
    private ElevatorSetpoints scoringState;

    public ScoringPositionCommand(ElevatorSetpoints scoringState) {
        addRequirements(scoring);
        this.scoringState = scoringState;
    }

    @Override
    public void execute() {

        switch (scoringState) {
            case Amp:
                scoring.setSetpoint(ElevatorSetpoints.Amp.value);
                break;
            case Ground:
                scoring.setSetpoint(ElevatorSetpoints.Ground.value);
                break;
            default:
                scoring.setSetpoint(ElevatorSetpoints.Trap.value);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return scoring.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
    }
}
