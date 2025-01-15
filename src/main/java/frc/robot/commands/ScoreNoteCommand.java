package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Storage;

public class ScoreNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Storage storage = Storage.getInstance();
    private ScoringLocation scoringLoc;

    public ScoreNoteCommand(ScoringLocation scoringLoc) {
        this.addRequirements(scoring);
        this.scoringLoc = scoringLoc;
    }

    @Override
    public void execute() {
        if (storage.noteExitingStorage()) {
            storage.runStorage();
            switch (scoringLoc) {
                case Amp:
                    scoring.feedForAmp();
                    break;
                case Trap:
                    scoring.feedForTrap();
                    break;
            }
        } else {
            storage.stopStorage();
            switch (scoringLoc) {
                case Amp:
                    scoring.scoreAmp();
                    break;
                case Trap:
                    scoring.scoreTrap();
                    break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stop();
        storage.stopStorage();
    }
}
