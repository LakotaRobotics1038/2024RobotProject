package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class RotateAuton extends Auton {
    private double initialPose;

    public RotateAuton(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToAmpTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());

        super.addCommands(
                followPathCommand(Paths.rotate()));
    }

}
