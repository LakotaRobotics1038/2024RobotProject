package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootPasserCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class MidlineNoteAuton extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();

    public MidlineNoteAuton(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromSouceToBottomMidlineNote());

        super.addCommands(
                followPathCommand(Paths.pathFromSourceToBottomNote),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromBottomNoteTo2ndBottomNote)
                                .until(acquisition::isNotePresent),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable),
                        new ShootPasserCommand(),
                        new AcquisitionRunCommand()),
                new ParallelCommandGroup(followPathCommand(Paths.pathFrom2ndBottomNoteToMiddleNote)
                        .until(acquisition::isNotePresent),
                        new AcquisitionRunCommand()),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 3),
                followPathCommand(Paths.pathFromAmpToMidlineAcquire)
                        .until(acquisition::isNotePresent)
                        .alongWith(new AcquisitionRunCommand()));

    }
}