package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class RotateCommand extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private double degree;
    private Timer timer = new Timer();

    public RotateCommand(double degree) {
        this.degree = degree;
        timer.start();
    }

    @Override
    public void execute() {
        timer.restart();
        while (timer.get() < .05) {
            driveTrain.drive(0, 0, 0.75, false);
        }
    }

    @Override
    public boolean isFinished() {
        if (driveTrain.getHeading() > degree - 5 &&
                driveTrain.getHeading() < degree + 5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.drive(0, 0, 0, false);
    }
}