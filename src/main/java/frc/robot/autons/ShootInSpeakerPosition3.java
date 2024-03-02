package frc.robot.autons;

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

public class ShootInSpeakerPosition3 extends Auton {

    // TODO: May need to move this instance into constructor
    private double initialPose;

    public ShootInSpeakerPosition3(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition3ToSpeakerTrajectory());
        this.setInitialPose(Trajectories.getFromPosition3ToSpeakerTrajectory());

        addCommands(
                AutoBuilder.followPath(Paths.pathFromPosition3ToSpeaker),
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
                new ShootNoteCommand(),
                new WaitCommand(0.2),
                AutoBuilder.followPath(Paths.pathFromSpeakerToMidline));
    }

}