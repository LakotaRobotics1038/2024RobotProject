package frc.robot.autons;

import java.nio.file.Path;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import frc.robot.subsystems.Dashboard;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class TestableSinglePath extends Auton {

    public TestableSinglePath(Optional<Alliance> alliance) {
        super(alliance);

        PathPlannerPath pathToAmpFromStart = PathPlannerPath.fromPathFile("From position 1 to amp");

        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(pathToAmpFromStart,
                driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        Dashboard.getInstance().setTrajectory(trajectory);
        this.setInitialPose(trajectory);

        super.addCommands(
                AutoBuilder.followPath(pathToAmpFromStart));
    }
}
