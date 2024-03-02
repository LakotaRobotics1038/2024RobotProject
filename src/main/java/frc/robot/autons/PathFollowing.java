package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

public class PathFollowing extends Auton {

    public PathFollowing(Optional<Alliance> alliance) {
        super(alliance);
        // TODO Auto-generated constructor stub
        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(Paths.splinePath(),
                DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
        this.setInitialPose(trajectory);
        addCommands(
                AutoBuilder.followPath(Paths.splinePath()),
                new WaitCommand(0.5),
                AutoBuilder.followPath(Paths.driveForward()));

    }

}
