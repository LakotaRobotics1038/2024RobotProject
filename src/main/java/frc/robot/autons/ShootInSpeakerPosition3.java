package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.FullAcquireCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

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
                new ShootNoteCommand(3),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                AutoBuilder.followPath(Paths.pathFromSpeakerToNote2),
                                AutoBuilder.followPath(Paths.pathFromNote2ToSpeaker)),
                        new FullAcquireCommand()),
                new ShootNoteCommand(3),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                AutoBuilder.followPath(Paths.pathFromSpeakerToNote3),
                                AutoBuilder.followPath(Paths.pathFromNote3ToSpeaker)),
                        new FullAcquireCommand()),
                new ShootNoteCommand(3),
                followPathCommand(Paths.pathFromSpeakerToMidline));
    }

}
