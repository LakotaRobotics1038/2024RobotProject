package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class SpinCommand extends PIDCommand {
    private static DriveTrain driveTrain = DriveTrain.getInstance();
    private static Vision vision = Vision.getInstance();

    private double gyroInit;
    private double gyroEnd;
    private boolean clockwise;
    private int id;

    public SpinCommand(int id) {
        super(new PIDController(0, 0, 0),
                driveTrain::getHeading,
                0 - vision.getAngle(id),
                output -> {
                    output = MathUtil.clamp(output, 0, 360);
                    driveTrain.drive(0, 0, output, true);
                },
                driveTrain, vision);
        getController().enableContinuousInput(0, 360);

        addRequirements(vision);
        this.id = id;
    }

    @Override
    public void initialize() {
        gyroInit = driveTrain.getRoll();
        gyroEnd = vision.getAngle(id) + gyroInit;

        if (gyroInit > gyroEnd) {
            clockwise = true;
        }
    }

    @Override
    public void execute() {
        if (clockwise == true) {
            driveTrain.drive(0, 0, 1, false);
        } else {
            driveTrain.drive(0, 0, -1, false);
        }
    }

    @Override
    public boolean isFinished() {
        if (driveTrain.getRoll() == gyroEnd) {
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
