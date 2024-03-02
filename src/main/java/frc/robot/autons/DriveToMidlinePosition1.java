package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class DriveToMidlinePosition1 extends Auton {

    // TODO: May need to move this instance into constructor
    private double initialPose;

    public DriveToMidlinePosition1(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToMidlineTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToMidlineTrajectory());

        addCommands(
                AutoBuilder.followPath(Paths.pathFromPosition1ToMidline));
    }

}