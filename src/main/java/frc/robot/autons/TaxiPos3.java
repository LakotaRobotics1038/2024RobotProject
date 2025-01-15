package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.constants.AutoPaths;

public class TaxiPos3 extends Auton {
    public TaxiPos3(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPos3TaxiTrajectory());

        addCommands(
                followPathCommand(AutoPaths.taxiPath3));
    }
}