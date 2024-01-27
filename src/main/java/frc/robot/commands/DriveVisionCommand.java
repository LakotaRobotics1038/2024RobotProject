package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.RecieveVisionDataReturnDrivingAround;

public class DriveVisionCommand extends PIDCommand {
    private static DriveTrain drivetrain = DriveTrain.getInstance();
    private RecieveVisionDataReturnDrivingAround recieveVisionDataReturnDrivingAround = RecieveVisionDataReturnDrivingAround
            .getInstance();

    private int id;

    public DriveVisionCommand(int id) {
        super(new PIDController(VisionConstants.kP, VisionConstants.kI, VisionConstants.kD),
                drivetrain::getRoll,
                VisionConstants.setpoint,
                output -> {
                    output = MathUtil.clamp(output, -VisionConstants.maxSpeed, VisionConstants.maxSpeed);
                    drivetrain.drive(output, 0, 0, true);
                },
                drivetrain);

        addRequirements(recieveVisionDataReturnDrivingAround);
        this.id = id;
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isFinished() {
        if (recieveVisionDataReturnDrivingAround.getDistance(id) == -1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(0, 0, 0, false);
    }
}
