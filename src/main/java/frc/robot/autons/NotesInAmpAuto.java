package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.path.PathPoint;
import com.pathplanner.lib.path.RotationTarget;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class NotesInAmpAuto extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private double rotationDistance = 270;

    public NotesInAmpAuto(Optional<Alliance> alliance, PathPlannerPath path) {
        super(alliance);

        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(path, driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));

        Dashboard.getInstance().setTrajectory(trajectory);
        PathPoint point = path.getPoint(0);
        driveTrain.zeroHeading(rotationDistance);
        super.addCommands(
                new SequentialCommandGroup(
                        followPathCommand("Position 1 to amp"),
                        new ScoreNoteAmpCommand()
                // new WaitCommand(0),
                // new ParallelCommandGroup(
                // followPathCommand("amp to note 1"),
                // new AcquireCommand()),
                // new WaitCommand(0),
                // followPathCommand("note 1 to amp"),
                // new ScoreNoteAmpCommand()
                ));

        this.setInitialPose(trajectory);
    }
}
