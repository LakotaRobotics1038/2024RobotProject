package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriveToMidlinePosition2 extends Auton {

    PathPlannerPath position2ToMidline = PathPlannerPath.fromPathFile("From position 2 to the midline");

    public DriveToMidlinePosition2(Optional<Alliance> alliance) {
        super(alliance);

        addCommands(
                AutoBuilder.followPath(position2ToMidline));

    }

}
