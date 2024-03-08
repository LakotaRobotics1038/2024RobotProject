package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.Scoring;

public class FeedNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Timer timer = new Timer();
    private double secondsToScore = 0;
    private ScoringLocation scoringLoc;

    public FeedNoteCommand(ScoringLocation scoringLoc, double secondsToScore) {
        this.addRequirements(scoring);
        this.scoringLoc = scoringLoc;
        this.secondsToScore = secondsToScore;
    }

    public FeedNoteCommand(ScoringLocation scoringLoc) {
        this.addRequirements(scoring);
        this.scoringLoc = scoringLoc;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        switch (scoringLoc) {
            case Amp:
                scoring.feedForApm();
                break;
            case Trap:
                scoring.feedForTrap();
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.secondsToScore != 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
        timer.stop();
        timer.reset();
    }
}
