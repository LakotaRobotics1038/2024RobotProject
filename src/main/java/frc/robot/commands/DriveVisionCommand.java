package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.RecieveVisionDataReturnDrivingAround;

public class DriveVisionCommand extends Command {
    private DriveTrain drivetrain = DriveTrain.getInstance();
    private RecieveVisionDataReturnDrivingAround recieveVisionDataReturnDrivingAround = RecieveVisionDataReturnDrivingAround
            .getInstance();
    private Timer timer;

    private int id;

    public DriveVisionCommand(int id) {
        addRequirements(recieveVisionDataReturnDrivingAround);
        this.id = id;
    }

    @Override
    public void execute() {
        drivetrain.drive(0, 1, 0, false);
    }

    @Override
    public boolean isFinished() {
        if (recieveVisionDataReturnDrivingAround.getDistance(id) == -1) {
            timer.start();
            if (timer.get() >= 0.5) {
                timer.stop();
                timer.reset();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(0, 0, 0, false);
    }
}