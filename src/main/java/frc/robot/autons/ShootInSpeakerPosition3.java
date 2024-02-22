package frc.robot.autons;

import java.nio.file.Path;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DriveVisionCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import frc.robot.subsystems.Vision.VisionTarget;

public class ShootInSpeakerPosition3 extends Auton {

    private double initialPose;

    public ShootInSpeakerPosition3(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition3ToSpeakerTrajectory());
        this.setInitialPose(Trajectories.getFromPosition3ToSpeakerTrajectory());

        addCommands(
                followPathCommand(Paths.pathFromPosition3ToSpeaker),
                new ShootNoteCommand(),
                followPathCommand(Paths.pathFromSpeakerToNote2),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote2ToSpeaker),
                        new ScoringPositionCommand(ElevatorSetpoints.Trap)),
                new StorageRunCommand(),
                new ShootNoteCommand(),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromSpeakerToNote3),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote3ToSpeaker),
                        new ScoringPositionCommand(ElevatorSetpoints.Trap)),
                new StorageRunCommand(),
                new ShootNoteCommand(),
                followPathCommand(Paths.pathFromSpeakerToMidline));
    }

}