package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoringElevatorPositionCommand extends Command {

    private ScoringElevator scoring = ScoringElevator.getInstance();
    private ElevatorSetpoints scoringState;

    public enum FinishActions {
        NoFinish,
        NoDisable,
        Default
    }

    private FinishActions finishAction = FinishActions.Default;

    public ScoringElevatorPositionCommand(ElevatorSetpoints scoringState) {
        addRequirements(scoring);
        this.scoringState = scoringState;
    }

    public ScoringElevatorPositionCommand(ElevatorSetpoints scoringState, FinishActions finishAction) {
        this(scoringState);
        this.finishAction = finishAction;
    }

    @Override
    public void initialize() {
        scoring.enable();
        scoring.setSetpoint(scoringState);
    }

    @Override
    public boolean isFinished() {
        return finishAction != FinishActions.NoFinish && scoring.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        if (finishAction != FinishActions.NoDisable) {
            scoring.disable();
        }
    }
}
