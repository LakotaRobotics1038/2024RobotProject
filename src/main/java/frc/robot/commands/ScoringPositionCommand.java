package frc.robot.commands;

import java.util.Timer;

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
    public void initialize() {

        // TODO Auto-generated method stub

        super.initialize();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        switch (scoringState) {
            case amp:
                scoring.setSetpoint(ElevatorSetpoints.amp.value);
                break;

            case ground:
                scoring.setSetpoint(ElevatorSetpoints.ground.value);
                break;

            default:
                scoring.setSetpoint(ElevatorSetpoints.trap.value);
                break;
        }
        super.execute();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return scoring.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        scoring.stopRoller();
        super.end(interrupted);
    }
}
