package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriveToMidlinePosition3 extends Auton {

    PathPlannerPath fromPositon3toMidline = PathPlannerPath.fromPathFile("From position 3 to midline");

    public DriveToMidlinePosition3(Optional<Alliance> alliance) {
        super(alliance);

        addCommands(
                AutoBuilder.followPath(fromPositon3toMidline));

    }

}
