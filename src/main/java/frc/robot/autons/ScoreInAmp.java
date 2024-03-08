package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Dashboard;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoreInAmp extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();

    public ScoreInAmp(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToAmpTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());
        // try .concatenate a return and final trajectory later

        super.addCommands(
                followPathCommand(Paths.pathFromPosition1ToAmp),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(1.5),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromAmpToNote1)
                                .until(acquisition::isNotePresent)
                                .andThen(followPathCommand(Paths.pathFromNote1ToAmp)),
                        // new FullAcquireCommand()),
                        new AcquisitionRunCommand()),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(3),
                followPathCommand(Paths.pathFromAmpToMidline));
    }
}