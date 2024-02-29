package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriveToMidlinePosition1 extends Auton {

    PathPlannerPath position1ToMidline = PathPlannerPath.fromPathFile("From position 1 to the midline");

    public DriveToMidlinePosition1(Optional<Alliance> alliance) {
        super(alliance);

        addCommands(
                AutoBuilder.followPath(position1ToMidline));

    }

}
