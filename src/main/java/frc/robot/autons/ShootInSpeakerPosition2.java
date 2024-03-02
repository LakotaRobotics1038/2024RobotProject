package frc.robot.autons;

import java.nio.file.Path;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;

public class ShootInSpeakerPosition2 extends Auton {

    // TODO: May need to move this instance into constructor
    private double initialPose;

    public ShootInSpeakerPosition2(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition2ToSpeakerTrajectory());
        this.setInitialPose(Trajectories.getFromPosition2ToSpeakerTrajectory());

        addCommands(
                AutoBuilder.followPath(Paths.pathFromPosition2ToSpeaker),
                new WaitCommand(0.2),
                new ShootNoteCommand(),
                new WaitCommand(0.2),
                AutoBuilder.followPath(Paths.pathFromSpeakerToNote2),
                new WaitCommand(0.2),
                new AcquireCommand(),
                new WaitCommand(0.2),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(Paths.pathFromNote2ToSpeaker),
                        // TODO: Determine whether we even need to run this command because this sets
                        // scoring position to TRAP
                        new ScoringPositionCommand(ElevatorSetpoints.Trap)),
                new WaitCommand(0.2),
                new StorageRunCommand(),
                new WaitCommand(0.2),
                new ShootNoteCommand(),
                new WaitCommand(0.2),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(Paths.pathFromSpeakerToNote3),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new WaitCommand(0.2),
                new AcquireCommand(),
                new WaitCommand(0.2),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(Paths.pathFromNote3ToSpeaker),
                        // TODO: Determine whether we even need to run this command because this sets
                        // scoring position to TRAP
                        new ScoringPositionCommand(ElevatorSetpoints.Trap)),
                new WaitCommand(0.2),
                new StorageRunCommand(),
                new WaitCommand(0.2),
                new ShootNoteCommand(),
                new WaitCommand(0.2),
                AutoBuilder.followPath(Paths.pathFromSpeakerToMidline));

    }
}