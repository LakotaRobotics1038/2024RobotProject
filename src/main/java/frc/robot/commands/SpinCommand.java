package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.RecieveVisionDataReturnDrivingAround;

public class SpinCommand extends Command {
    private RecieveVisionDataReturnDrivingAround recieveVisionDataReturnDrivingAround = RecieveVisionDataReturnDrivingAround
            .getInstance();
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private double gyroInit;
    private double gyroEnd;
    private double x;
    private double y;
    private boolean negativeOrNot;

    public SpinCommand(double x, double y) {
        addRequirements(recieveVisionDataReturnDrivingAround);
        this.x = x;
        this.y = y;
    }

    @Override
    public void initialize() {
        gyroInit = driveTrain.getRoll();
        gyroEnd = recieveVisionDataReturnDrivingAround.getAngle(x, y) + gyroInit;

        if (gyroInit > gyroEnd) {
            negativeOrNot = true;
        }
    }

    @Override
    public void execute() {
        if (negativeOrNot == true) {
            driveTrain.drive(1, 1, 1, false);
        } else {
            driveTrain.drive(1, 1, -1, false);
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
