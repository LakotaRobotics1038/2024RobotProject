package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoringPositionCommand extends Command {

    private ScoringElevator scoring = ScoringElevator.getInstance();
    private ElevatorSetpoints scoringState;

    public ScoringPositionCommand(ElevatorSetpoints scoringState) {
        addRequirements(scoring);
        this.scoringState = scoringState;
    }

    @Override
    public void initialize() {
        scoring.enable();
    }

    @Override
    public void execute() {
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
        return scoring.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        scoring.disable();
    }
}
