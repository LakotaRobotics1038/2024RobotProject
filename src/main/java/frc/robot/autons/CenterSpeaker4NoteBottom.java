package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootPasserCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class CenterSpeaker4NoteBottom extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();

    public CenterSpeaker4NoteBottom(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory(),
                new Rotation2d(Units.degreesToRadians(180)));

        super.addCommands(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable),
                new ShootPasserCommand(),

                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromMiddleSpeakerBottomNote)
                                .until(acquisition::isNotePresent)
                                .andThen(new ParallelCommandGroup(
                                        followPathCommand(Paths.pathFromBottomNoteToMiddleSpeaker),
                                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer,
                                                FinishActions.NoDisable)),
                                        new AcquisitionRunCommand()),
                        new ShootPasserCommand(),

                        new ParallelCommandGroup(
                                followPathCommand(Paths.pathFromMiddleSpeakerMiddleNote)
                                        .until(acquisition::isNotePresent)
                                        .andThen(new ParallelCommandGroup(
                                                followPathCommand(Paths.pathFromMiddleNoteToMiddleSpeaker),
                                                new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer,
                                                        FinishActions.NoDisable)),
                                                new AcquisitionRunCommand()),
                                new ShootPasserCommand())),

                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromMiddleSpeakerTopNote)
                                .until(acquisition::isNotePresent)
                                .andThen(new ParallelCommandGroup(
                                        followPathCommand(Paths.pathFromTopNoteToMiddleSpeaker),
                                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer,
                                                FinishActions.NoDisable)),
                                        new AcquisitionRunCommand()),
                        new ShootPasserCommand()));
    }
}