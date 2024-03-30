package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.DriverJoystick;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionTarget;

public class DriveToAprilTagCommand extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    private XboxController1038 joystick;
    private VisionTarget aprilVisionTarget;
    private double prevAngle = 0;
    private SlewRateLimiter forwardFilter = new SlewRateLimiter(1.0);
    private PIDController sidewaysController = new PIDController(0, 0, 0);
    private PIDController rotateController = new PIDController(0.03, 0, 0);
    private ShuffleboardTab tempTab = Shuffleboard.getTab("Temp");

    // Previous Status
    private double prevY = 0;

    public DriveToAprilTagCommand(XboxController1038 joystick, VisionTarget aprilVisionTarget) {
        addRequirements(driveTrain);
        this.joystick = joystick;
        this.aprilVisionTarget = aprilVisionTarget;
        rotateController.enableContinuousInput(0, 360);
        tempTab.add("sideways", sidewaysController)
                .withPosition(0, 0);
        tempTab.add("rotate", rotateController)
                .withPosition(1, 0);
    }

    @Override
    public void initialize() {
        vision.enable();
        rotateController.reset();
        sidewaysController.reset();
        forwardFilter.reset(0);
        prevAngle = 0;
        rotateController.setSetpoint(0);
    }

    @Override
    public void execute() {
        double y = joystick.getLeftY();
        double angle = vision.getAngle(aprilVisionTarget);
        if (angle != 0 && angle != prevAngle) {
            prevAngle = angle;
            rotateController.setSetpoint(
                    // Use Rotation2d to ensure setpoint is between 0 and 360
                    Rotation2d.fromDegrees(driveTrain.getHeading())
                            .minus(Rotation2d.fromDegrees(angle))
                            .getDegrees());
        }
        System.out.println("ANGL: " + angle);
        if (rotateController.getSetpoint() == 0) {
            return;
        }

        double forward = DriverJoystick.getInstance().limitRate(y, prevY, forwardFilter);
        double sideways = sidewaysController.calculate(vision.getY(aprilVisionTarget));
        double rotate = rotateController.calculate(driveTrain.getHeading());

        prevY = y;
        driveTrain.drive(forward, -sideways, rotate, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        vision.disable();
        driveTrain.drive(0, 0, 0, true);
    }
}
