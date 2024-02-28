package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Scoring;

public class OpenFlopsCommand extends Command {
    private Scoring scoring = Scoring.getInstance();

    public OpenFlopsCommand() {
        this.addRequirements(scoring);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        scoring.scoreAmp();
    }

    @Override
    public boolean isFinished() {
        System.out.println(scoring.getPosition());

        return scoring.getPosition() >= 3;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stopRoller();
    }
}
