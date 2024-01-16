package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants;
import frc.robot.subsystems.Scoring;

public class ScoreNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Timer timer = new Timer();
    private int secondsToScore;

    public ScoreNoteCommand(int secondsToScore) {
        this.addRequirements(scoring);
        this.secondsToScore = secondsToScore;
    }

    public ScoreNoteCommand() {
        this.addRequirements(scoring);

    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        scoring.runRoller(ScoringConstants.rollerSpeed);
        scoring.runLoader(ScoringConstants.loaderSpeed);
    }

    @Override
    public boolean isFinished() {
        return this.secondsToScore == 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
        scoring.stopLoader();
        timer.stop();
        timer.reset();
    }
}
