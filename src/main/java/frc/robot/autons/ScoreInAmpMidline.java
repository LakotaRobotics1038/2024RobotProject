package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.AutoPaths;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoreInAmpMidline extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();
    private Alliance alliance;

    public ScoreInAmpMidline(Optional<Alliance> alliance) {
        super(alliance);

        this.alliance = alliance.get();
        if (this.alliance == Alliance.Blue) {
            this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory(),
                    new Rotation2d(Units.degreesToRadians(270)));
        } else if (this.alliance == Alliance.Red) {
            this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory(),
                    new Rotation2d(Units.degreesToRadians(90)));
        }

        super.addCommands(
                followPathCommand(AutoPaths.pathFromPosition1ToAmp),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 1.5),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                new ParallelCommandGroup(
                        followPathCommand(AutoPaths.pathFromAmpToMidlineAcquire)
                                .until(acquisition::isNotePresent)
                                .andThen(followPathCommand(AutoPaths.pathFromMidlineNoteToAmp)),
                        new AcquisitionRunCommand()),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 3),
                followPathCommand(AutoPaths.pathFromAmpToMidlineAcquire));
    }
}