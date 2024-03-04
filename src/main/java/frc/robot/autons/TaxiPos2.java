package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class TaxiPos2 extends Auton {
    public TaxiPos2(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos2TaxiTrajectory());
        this.setInitialPose(Trajectories.getFromPos1TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath2));
    }
}