package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoringElevatorPositionCommand extends Command {

    private ScoringElevator scoring = ScoringElevator.getInstance();
    private ElevatorSetpoints scoringState;

    private boolean noFinish = true;

    public ScoringElevatorPositionCommand(ElevatorSetpoints scoringState, boolean noFinish) {
        addRequirements(scoring);
        this.scoringState = scoringState;
        this.noFinish = noFinish;

    }

    public ScoringElevatorPositionCommand(ElevatorSetpoints scoringState) {
        addRequirements(scoring);
        this.scoringState = scoringState;
    }

    @Override
    public void initialize() {
        scoring.enable();
        switch (scoringState) {
            case Amp:
                scoring.setSetpoint(ElevatorSetpoints.Amp);
                break;
            case Ground:
                scoring.setSetpoint(ElevatorSetpoints.Ground);
                break;
            default:
                scoring.setSetpoint(ElevatorSetpoints.Trap);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return !noFinish && scoring.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        scoring.disable();
    }
}
