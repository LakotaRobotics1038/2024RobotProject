package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.constants.AutoPaths;

public class TaxiPos2 extends Auton {
    public TaxiPos2(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPos1TaxiTrajectory());

        addCommands(
                followPathCommand(AutoPaths.taxiPath2));
    }
}