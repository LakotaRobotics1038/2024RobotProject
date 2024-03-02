package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class TaxiPos2 extends Auton {

    // TODO: May need to move this instance into constructor
    private double initialPose;

    public TaxiPos2(Optional<Alliance> alliance) {
        super(alliance);

        // this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos2TaxiTrajectory());
        this.setInitialPose(Trajectories.getFromPos1TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath2));
    }

}