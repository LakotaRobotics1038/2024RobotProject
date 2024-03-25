package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
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
    private SlewRateLimiter forwardFilter = new SlewRateLimiter(1.0);
    private PIDController sidewaysController = new PIDController(0, 0, 0);
    private PIDController rotateController = new PIDController(0, 0, 0);

    // Previous Status
    private double prevY = 0;

    public DriveToAprilTagCommand(XboxController1038 joystick, VisionTarget aprilVisionTarget) {
        addRequirements(driveTrain);
        this.joystick = joystick;
        this.aprilVisionTarget = aprilVisionTarget;
    }

    @Override
    public void initialize() {
        vision.enable1();
        rotateController.reset();
        sidewaysController.reset();
        forwardFilter.reset(0);
    }

    @Override
    public void execute() {
        double y = joystick.getLeftY();

        double forward = DriverJoystick.getInstance().limitRate(y, prevY, forwardFilter);
        double sideways = sidewaysController.calculate(vision.getY(aprilVisionTarget));
        double rotate = rotateController.calculate(vision.getAngle(aprilVisionTarget));

        prevY = y;
        driveTrain.drive(forward, -sideways, -rotate, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        vision.disable1();
        driveTrain.drive(0, 0, 0, true);
    }
}
