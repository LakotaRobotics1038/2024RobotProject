package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;

public class FeedNoteFineAdjCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private double startPos;

    public FeedNoteFineAdjCommand() {
        this.addRequirements(scoring);
    }

    @Override
    public void initialize() {
        this.startPos = scoring.getPosition();
    }

    @Override
    public void execute() {
        scoring.feedForAmp();
    }

    @Override
    public boolean isFinished() {
        return scoring.getPosition() <= this.startPos - 0.05;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
    }
}
