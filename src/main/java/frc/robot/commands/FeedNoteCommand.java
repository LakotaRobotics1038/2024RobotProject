package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.Scoring;

public class FeedNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private ScoringLocation scoringLoc;

    public FeedNoteCommand(ScoringLocation scoringLoc) {
        this.addRequirements(scoring);
        this.scoringLoc = scoringLoc;
    }

    @Override
    public void execute() {
        switch (scoringLoc) {
            case Amp:
                scoring.feedForAmp();
                break;
            case Trap:
                scoring.feedForTrap();
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stop();
    }
}
