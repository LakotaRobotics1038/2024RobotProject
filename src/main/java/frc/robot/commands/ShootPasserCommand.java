package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Passer;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Storage;

public class ShootPasserCommand extends Command {
    private Scoring scoring = Scoring.getInstance();
    private Storage storage = Storage.getInstance();
    private Passer passer = Passer.getInstance();
    private Timer timer = new Timer();
    private double secondsToScore = 0;

    public ShootPasserCommand(double secondsToScore) {
        this.addRequirements(scoring);
        this.secondsToScore = secondsToScore;
    }

    public ShootPasserCommand() {
        this.addRequirements(scoring);
    }

    @Override
    public void initialize() {
        timer.start();
        passer.shoot();
    }

    @Override
    public void execute() {
        if (timer.get() > 0.25) {
            scoring.feedForPasser();
            storage.runStorage();
        }
    }

    @Override
    public boolean isFinished() {
        return this.secondsToScore != 0 ? timer.get() > this.secondsToScore : false;
    }

    @Override
    public void end(boolean interrupted) {
        scoring.stop();
        storage.stopStorage();
        passer.stop();
        timer.stop();
        timer.reset();
    }
}