package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionTarget;

public class DriveToAprilTagVisionAuton extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();
    private PIDController dController = new PIDController(VisionConstants.driveP, VisionConstants.driveI,
            VisionConstants.driveD);
    private PIDController strafeController = new PIDController(VisionConstants.driveP, VisionConstants.driveI,
            VisionConstants.driveD);
    private PIDController rotController = new PIDController(VisionConstants.spinP, VisionConstants.spinI,
            VisionConstants.spinD);
    private VisionTarget target;
    private double targetHeight;
    private double setpointToleranceX;

    public DriveToAprilTagVisionAuton(VisionTarget target, double targetHeight, double setpointToleranceX) {
        addRequirements(driveTrain, vision);
        this.target = target;
        this.targetHeight = targetHeight;
        this.setpointToleranceX = setpointToleranceX;
    }

    public void initialize() {
        vision.enable0();
        dController.reset();
        strafeController.reset();
        rotController.reset();
    }

    public void execute() {
        double driveOutput = dController.calculate(driveTrain.getPose().getX(),
                driveTrain.getPose().getX() + vision.getVerticalDistance(target, targetHeight) - setpointToleranceX);
        double strafeOutput = strafeController.calculate(driveTrain.getPose().getY(),
                driveTrain.getPose().getY() + vision.getHorizontalDistance(target, targetHeight));
        double rotOutput = rotController.calculate(driveTrain.getHeading(), vision.getAngle(target));

        driveTrain.drive(driveOutput, -strafeOutput, -rotOutput, false);
    }

    public boolean isFinished() {
        if (dController.atSetpoint() && strafeController.atSetpoint() && rotController.atSetpoint()) {
            return true;
        }
        return false;
    }

    public void end() {
        driveTrain.drive(0, 0, 0, false);
        vision.disable0();
    }

}
