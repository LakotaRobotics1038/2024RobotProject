package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class DriveVisionCommand extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();
    private Timer timer;

    private int id;

    public DriveVisionCommand(int id) {
        addRequirements(vision);
        this.id = id;
    }

    @Override
    public void execute() {
        driveTrain.drive(0, 1, 0, false);
    }

    @Override
    public boolean isFinished() {
        if (vision.getDistance(id) == -1) {
            timer.start();
            if (timer.get() >= 0.5) {
                timer.stop();
                timer.reset();
                return true;
            } else {
                return false;
            }
        } else if (vision.getDistance(id) >= VisionConstants.aprilTagArea) {
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
