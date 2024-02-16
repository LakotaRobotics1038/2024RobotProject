package frc.robot.autons.Paths;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.path.PathPoint;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.autons.Auton;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class PosOneToAmp extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    public PosOneToAmp(Optional<Alliance> alliance, PathPlannerPath path) {
        super(alliance);
        double initialPose = 90;
        driveTrain.zeroHeading(initialPose);
        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(path, driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        Dashboard.getInstance().setTrajectory(trajectory);

        PathPoint point = path.getPoint(0);
        super.addCommands(
                new SequentialCommandGroup(
                        followPathCommand("Position 1 to amp")));

        this.setInitialPose(trajectory);
    }
}
