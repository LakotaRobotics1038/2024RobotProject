package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;

public class ScoreNoteCommandRunningBackward extends Command {
    Scoring scoring = Scoring.getInstance();
    private Timer timer = new Timer();
    private double secondsToScore;
    private ElevatorSetpoints elevatorSetpoint;

    public ScoreNoteCommandRunningBackward(double secondsToScore, ElevatorSetpoints elevatorSetpoint) {
        addRequirements(scoring);
        this.secondsToScore = secondsToScore;
        this.elevatorSetpoint = elevatorSetpoint;
    }

    public ScoreNoteCommandRunningBackward(ElevatorSetpoints elevatorSetpoint) {
        addRequirements(scoring);
        this.elevatorSetpoint = elevatorSetpoint;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if (elevatorSetpoint.equals(ElevatorSetpoints.amp)) {
            scoring.setSetpoint(ElevatorSetpoints.amp.value);
        }

        // Waiting on methods for these
        // scoring.runRollerBackwardToShoot(-ScoringConstants.rollerSpeed);
        // scoring.runLoaderBackward(-ScoringConstants.loaderSpeed);

    }

    @Override
    public boolean isFinished() {
        return this.secondsToScore == 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopLoader();
        scoring.stopRoller();
    }

}
