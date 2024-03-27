package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.DriveToAprilTagVisionAuton;
import frc.robot.subsystems.Vision.VisionTarget;

public class DriveToAprilTagVision extends Auton {

    public DriveToAprilTagVision(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPosition1ToFrontAmpTrajectory());
        addCommands(
                followPathCommand(Paths.pathFromPosition1ToFrontAmp)
                        .andThen(new DriveToAprilTagVisionAuton(VisionTarget.APT5, 1, 0.2)));

    }

}
