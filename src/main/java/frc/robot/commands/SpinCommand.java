package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.RecieveVisionDataReturnDrivingAround;
import frc.robot.subsystems.Vision.VisionTarget;

public class SpinCommand extends Command {
    private RecieveVisionDataReturnDrivingAround recieveVisionDataReturnDrivingAround = RecieveVisionDataReturnDrivingAround
            .getInstance();
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private double gyroInit;
    private double gyroEnd;
    private boolean clockwise;
    private VisionTarget id;

    public SpinCommand(VisionTarget id) {
        addRequirements(recieveVisionDataReturnDrivingAround);
        this.id = id;
    }

    @Override
    public void initialize() {
        gyroInit = driveTrain.getRoll();
        gyroEnd = recieveVisionDataReturnDrivingAround.getAngle(id.getValue()) + gyroInit;

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
