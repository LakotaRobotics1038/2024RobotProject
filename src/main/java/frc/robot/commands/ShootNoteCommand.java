package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ShootNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Storage storage = Storage.getInstance();
    private ScoringElevator scoringElevator = ScoringElevator.getInstance();
    private Timer timer = new Timer();
    private int secondsToScore = 0;

    public ShootNoteCommand() {
        this.addRequirements(scoring);
    }

    public ShootNoteCommand(int secondsToScore) {
        addRequirements(scoring);
        this.secondsToScore = secondsToScore;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if (scoringElevator.getLeftPosition() != ElevatorSetpoints.Ground.value) {
            scoring.scoreSpeaker();
            if (timer.get() >= 2) {
                storage.runStorage();
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (scoringElevator.getLeftPosition() == ElevatorSetpoints.Ground.value) {
            return true;
        }
        return this.secondsToScore != 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
        timer.stop();
        timer.reset();
        storage.stopStorage();
    }
}
