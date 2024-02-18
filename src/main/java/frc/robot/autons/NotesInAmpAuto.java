package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.path.PathPoint;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class NotesInAmpAuto extends Auton {
    private static DriveTrain driveTrain = DriveTrain.getInstance();

    public NotesInAmpAuto(Optional<Alliance> alliance) {
        super(alliance);
        double initialPose = 270;
        driveTrain.zeroHeading(initialPose - 180);

        PathPlannerPath posOneToAmpPath = Trajectories.posOneToAmp();
        PathPlannerPath apmToNoteOnePath = Trajectories.ampToNoteOne();

        // why arent you doing this in the trajectories file
        // why does your trajectories file actually return paths
        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(posOneToAmpPath,
                driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        Dashboard.getInstance().setTrajectory(trajectory);
        this.setInitialPose(trajectory);

        super.addCommands(
                followPathCommand(posOneToAmpPath),
                new ScoreNoteAmpCommand(),
                new WaitCommand(2),
                new ParallelRaceGroup(
                        followPathCommand(apmToNoteOnePath),
                        new AcquireCommand()),
                new WaitCommand(2)
        // new NoteOneToAmp(alliance),
        // new ScoreNoteAmpCommand()
        );
    }
}
