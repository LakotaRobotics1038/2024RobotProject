package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class FromAmpToMidOverNote extends Auton {

    // TODO: May need to move this instance into constructor
    // private double initialPose;

    public FromAmpToMidOverNote(Optional<Alliance> alliance) {
        super(alliance);
        // double initialPose = 90.0;
        // driveTrain.zeroHeadingManual(initialPose - 180);
        // this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromAmpToMidlineOverNoteTrajectory());
        this.setInitialPose(Trajectories.getFromAmpToMidlineOverNoteTrajectory());

        addCommands(
                followPathCommand(Paths.pathFromAmpRollingOverNoteToMidline));
    }

}