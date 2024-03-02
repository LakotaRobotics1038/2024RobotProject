package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class DriveToMidlinePosition3 extends Auton {

    // TODO: May need to move this instance into constructor
    private int initialPose;

    public DriveToMidlinePosition3(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition3ToMidlineTrajectory());
        this.setInitialPose(Trajectories.getFromPosition3ToMidlineTrajectory());

        addCommands(
                AutoBuilder.followPath(Paths.pathFromPosition3ToMidline));
    }

}