package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import frc.robot.constants.ScoringConstants;
import frc.robot.constants.ScoringConstants.ScoringStatesConstants;

public class ScoringPositionCommand extends Command {

    private Scoring scoring = Scoring.getInstance();
    private ScoringStatesConstants scoringState;

    public ScoringPositionCommand(ScoringStatesConstants scoringState) {
        addRequirements(scoring);
        this.scoringState = scoringState;
    }

    @Override
    public void initialize() {
        switch (scoringState) {
            case Amp:
                scoring.setSetpoint(ElevatorSetpoints.amp);
                break;

            default:
                break;
        }
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
