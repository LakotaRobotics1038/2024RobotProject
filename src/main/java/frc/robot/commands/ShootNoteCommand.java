package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ScoringConstants;
import frc.robot.subsystems.Scoring;

public class ShootNoteCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Timer timer = new Timer();
    private int secondsToScore;

    public ShootNoteCommand(int secondsToScore) {
        this.addRequirements(scoring);
        this.secondsToScore = secondsToScore;
    }

    public ShootNoteCommand() {
        this.addRequirements(scoring);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
      if (scoring.getPosition() != ScoringConstants.groundSetpoint) {
          scoring.rollerShoot();
      }

      // can i run the position command inside this command
    }

    @Override
    public boolean isFinished() {
      if (scoring.getPosition() == ScoringConstants.groundSetpoint) {
          return true;
      }
        return this.secondsToScore == 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
        timer.stop();
        timer.reset();
    }
}
