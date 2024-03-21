package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class TaxiPos3 extends Auton {
    public TaxiPos3(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos3TaxiTrajectory());
        this.setInitialPose(Trajectories.getFromPos3TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath3));
    }
}