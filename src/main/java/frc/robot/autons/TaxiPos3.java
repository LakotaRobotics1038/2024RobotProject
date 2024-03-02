package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class TaxiPos3 extends Auton {

    // TODO: May need to move this instance into constructor
    private double initialPose;

    public TaxiPos3(Optional<Alliance> alliance) {
        super(alliance);

        // this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos3TaxiTrajectory());
        this.setInitialPose(Trajectories.getFromPos3TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath3));
    }

}