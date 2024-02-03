package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class RelinquishCommand extends Command {
    private Acquisition acquisition = Acquisition.getInstance();
    private Timer timer = new Timer();
    private double timeToDrop = 1;

    public RelinquishCommand() {
        addRequirements(acquisition);
    }

    public RelinquishCommand(int timeToDrop) {
        addRequirements(acquisition);
        this.timeToDrop = timeToDrop;
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        acquisition.reverseMotors();
    }

    @Override
    public boolean isFinished() {

        return (timeToDrop == 0) || (timeToDrop < timer.get());
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stop();
        timer.stop();
        timer.reset();
    }
}