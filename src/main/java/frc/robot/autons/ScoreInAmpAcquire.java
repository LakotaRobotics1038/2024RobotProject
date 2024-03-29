package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoreInAmpAcquire extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();

    public ScoreInAmpAcquire(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());

        super.addCommands(
                followPathCommand(Paths.pathFromPosition1ToAmp),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 1.5),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromAmpToNote1)
                                .until(acquisition::isNotePresent)
                                .andThen(followPathCommand(Paths.pathFromNote1ToAmp)),
                        new AcquisitionRunCommand()),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 3),
                followPathCommand(Paths.pathFromAmpToMidlineAcquire)
                        .until(acquisition::isNotePresent)
                        .alongWith(new AcquisitionRunCommand()));

    }
}