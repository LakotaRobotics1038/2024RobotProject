package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class DriveToMidlinePosition2 extends Auton {

    // TODO: May need to move this instance into constructor
    private int initialPose;

    public DriveToMidlinePosition2(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition2ToMidlineTrajectory());
        this.setInitialPose(Trajectories.getFromPosition2ToMidlineTrajectory());

        addCommands(
                followPathCommand(Paths.pathFromPosition2ToMidline));
    }

}