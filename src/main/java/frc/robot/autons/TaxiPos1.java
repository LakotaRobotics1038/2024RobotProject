package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

public class TaxiPos1 extends Auton {
    public TaxiPos1(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos1TaxiTrajectory());
        this.setInitialPose(Trajectories.getFromPos1TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath1));
    }

}