package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Storage;

public class ScoreNoteAmpCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Storage storage = Storage.getInstance();
    private Timer timer = new Timer();
    private int secondsToScore = 0;

    public ScoreNoteAmpCommand(int secondsToScore) {
        this.addRequirements(scoring);
        this.secondsToScore = secondsToScore;
    }

    public ScoreNoteAmpCommand() {
        this.addRequirements(scoring);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if (storage.noteExitingStorage()) {
            storage.runStorage();
            scoring.feedForApm();
        } else {
            storage.stopStorage();
            scoring.scoreAmp();
        }
    }

    @Override
    public boolean isFinished() {
        return this.secondsToScore != 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
        storage.stopStorage();
        timer.stop();
        timer.reset();
    }
}
