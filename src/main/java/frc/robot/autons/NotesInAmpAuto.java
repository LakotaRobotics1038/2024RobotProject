package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class NotesInAmpAuto extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    public NotesInAmpAuto(Optional<Alliance> alliance, PathPlannerPath path) {
        super(alliance);

        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(path, driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));

        Dashboard.getInstance().setTrajectory(trajectory);

        driveTrain.zeroHeading();
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
