package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.path.PathPoint;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.autons.Paths.AmpToNoteOne;
import frc.robot.autons.Paths.NoteOneToAmp;
import frc.robot.autons.Paths.PosOneToAmp;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class NotesInAmpAuto extends Auton {
    private static DriveTrain driveTrain = DriveTrain.getInstance();

    public NotesInAmpAuto(Optional<Alliance> alliance, PathPlannerPath initialPath) {
        super(alliance);
        double initialPose = 90;
        driveTrain.zeroHeading(initialPose);
        PathPlannerTrajectory trajectory1 = new PathPlannerTrajectory(initialPath,
                driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        Dashboard.getInstance().setTrajectory(trajectory1);

        super.addCommands(
                new SequentialCommandGroup(
                        new PosOneToAmp(alliance, Trajectories.posOneToAmp()),
                        new ScoreNoteAmpCommand(),
                        new WaitCommand(2),
                        new ParallelCommandGroup(
                                new AmpToNoteOne(alliance, Trajectories.ampToNoteOne()),
                                new AcquireCommand()),
                        new WaitCommand(2)
                // new NoteOneToAmp(alliance, Trajectories.noteOneToAmp()),
                // new ScoreNoteAmpCommand()
                ));
        this.setInitialPose(trajectory1);

    }
}
