package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.DriverJoystick;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionTarget;

public class DriveToNoteAuton extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    public DriveToNoteAuton() {
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        vision.enable0();
    }

    @Override
    public void execute() {
        double value = vision.getDistance(VisionTarget.NOTE);
        if (value == 0) {
            driveTrain.drive(0, 1, 0, false);
        }
    }

    @Override
    public boolean isFinished() {
        return vision.getDistance(VisionTarget.NOTE) == 1 ? true : false;
    }

    @Override
    public void end(boolean interrupted) {
        vision.disable0();
        driveTrain.drive(0, 0, 0, true);
    }
}
