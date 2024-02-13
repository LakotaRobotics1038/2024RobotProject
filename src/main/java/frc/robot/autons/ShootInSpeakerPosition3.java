package frc.robot.autons;

import java.nio.file.Path;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DriveVisionCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import frc.robot.subsystems.Vision.VisionTarget;

public class ShootInSpeakerPosition3 extends Auton {

    public ShootInSpeakerPosition3(Alliance alliance) {
        super(alliance);

        PathPlannerPath startToShooting = PathPlannerPath.fromPathFile("From position 3 to speakre");
        PathPlannerPath shootingToNoteTwo = PathPlannerPath.fromPathFile("From speaker to middle note");
        PathPlannerPath noteTwoToShooting = PathPlannerPath.fromPathFile("From middle note to speaker");
        PathPlannerPath shootingToNoteThree = PathPlannerPath.fromPathFile("From speaker to bottom note");
        PathPlannerPath noteThreeToShooting = PathPlannerPath.fromPathFile("From bottom note to speaker");
        PathPlannerPath shootingToMidline = PathPlannerPath.fromPathFile("From speaker to midline");

        addCommands(
                new ParallelRaceGroup(
                        AutoBuilder.followPath(startToShooting),
                        new DriveVisionCommand(VisionTarget.APR1)),
                new ShootNoteCommand(),
                new ParallelRaceGroup(
                        AutoBuilder.followPath(shootingToNoteTwo),
                        new DriveVisionCommand(VisionTarget.NOTES)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                AutoBuilder.followPath(noteTwoToShooting),
                                new DriveVisionCommand(VisionTarget.APR1)),
                        new ScoringPositionCommand(ElevatorSetpoints.trap)),
                new StorageRunCommand(),
                new ShootNoteCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                AutoBuilder.followPath(shootingToNoteThree),
                                new DriveVisionCommand(VisionTarget.NOTES)),
                        new ScoringPositionCommand(ElevatorSetpoints.ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                AutoBuilder.followPath(noteThreeToShooting),
                                new DriveVisionCommand(VisionTarget.APR1)),
                        new ScoringPositionCommand(ElevatorSetpoints.trap)),
                new StorageRunCommand(),
                new ShootNoteCommand(),
                AutoBuilder.followPath(shootingToMidline)

        );
    }

}
