package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootPasserCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class MidlineNoteAutonAmp extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();
    private Storage storage = Storage.getInstance();

    public MidlineNoteAutonAmp(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromSouceToBottomMidlineNote());

        super.addCommands(
                followPathCommand(Paths.pathFromPos1ToTopNote),

                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromTopNoteTo2ndTopNote)
                                .until(acquisition::isNotePresent),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable)
                                .onlyIf(() -> storage.noteExitingStorage() || acquisition.isNotePresent())
                                .alongWith(new ShootPasserCommand()),
                        new AcquisitionRunCommand()),

                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFrom2ndTopNoteToMiddleNote)
                                .until(acquisition::isNotePresent),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable)
                                .onlyIf(() -> storage.noteExitingStorage() || acquisition.isNotePresent())
                                .alongWith(new ShootPasserCommand()),
                        new AcquisitionRunCommand()),

                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromMiddleNoteTo2ndBottomNote)
                                .until(acquisition::isNotePresent),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable)
                                .onlyIf(() -> storage.noteExitingStorage() || acquisition.isNotePresent())
                                .alongWith(new ShootPasserCommand()),
                        new AcquisitionRunCommand()));

    }
}