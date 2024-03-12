package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.DriverJoystick;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionTarget;

public class DriveToNoteCommand extends Command {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    private XboxController1038 joystick;
    private SlewRateLimiter forwardFilter = new SlewRateLimiter(1.0);
    private SlewRateLimiter sidewaysFilter = new SlewRateLimiter(1.0);
    private PIDController controller = new PIDController(0, 0, 0);

    // Previous Status
    private double prevX = 0;
    private double prevY = 0;

    public DriveToNoteCommand(XboxController1038 joystick) {
        addRequirements(driveTrain);
        this.joystick = joystick;
    }

    @Override
    public void initialize() {
        vision.enable0();
        controller.reset();
        forwardFilter.reset(0);
        sidewaysFilter.reset(0);
    }

    @Override
    public void execute() {
        double x = joystick.getLeftX();
        double y = joystick.getLeftY();

        double forward = DriverJoystick.limitRate(y, prevY, forwardFilter);
        double sideways = DriverJoystick.limitRate(x, prevX, sidewaysFilter);
        double rotate = controller.calculate(vision.getAngle(VisionTarget.NOTE));

        prevX = x;
        prevY = y;
        driveTrain.drive(forward, -sideways, -rotate, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        vision.disable0();
        driveTrain.drive(0, 0, 0, true);
    }
}
