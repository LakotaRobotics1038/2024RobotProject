package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
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
    private double verticalDistance;
    private double horizontalDistance;
    private double prevAngle;

    public DriveToAprilTagVisionAuton(VisionTarget target, double targetHeight, double setpointToleranceX) {
        addRequirements(driveTrain, vision);
        this.target = target;
        this.targetHeight = targetHeight;
        this.setpointToleranceX = setpointToleranceX;
    }

    public void initialize() {
        vision.enable();
        dController.reset();
        strafeController.reset();
        rotController.reset();

        this.verticalDistance = driveTrain.getPose().getX() + vision.getVerticalDistance(target, targetHeight)
                - setpointToleranceX;
        this.horizontalDistance = driveTrain.getPose().getY() + vision.getHorizontalDistance(target, targetHeight);
        dController.setSetpoint(verticalDistance);
        strafeController.setSetpoint(horizontalDistance);
    }

    public void execute() {
        double rotationAmount = vision.getAngle(target);
        if (rotationAmount != 0 && rotationAmount != prevAngle) {
            prevAngle = rotationAmount;
            rotController.setSetpoint(
                    // Use Rotation2d to ensure setpoint is between 0 and 360
                    Rotation2d.fromDegrees(driveTrain.getHeading())
                            .minus(Rotation2d.fromDegrees(rotationAmount))
                            .getDegrees());
        }

        double driveOutput = dController.calculate(driveTrain.getPose().getX());
        double strafeOutput = strafeController.calculate(driveTrain.getPose().getY());
        double rotOutput = rotController.calculate(driveTrain.getHeading());

        driveTrain.drive(driveOutput, -strafeOutput, rotOutput, false);
    }

    public boolean isFinished() {
        return false;
    }

    public void end() {
        driveTrain.drive(0, 0, 0, false);
        vision.disable();
    }

}
