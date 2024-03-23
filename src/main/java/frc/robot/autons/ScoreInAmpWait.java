package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Dashboard;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoreInAmpWait extends Auton {

    public ScoreInAmpWait(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToAmpTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());
        // try .concatenate a return and final trajectory later

        super.addCommands(
                new WaitCommand(5),
                followPathCommand(Paths.pathFromPosition1ToAmp),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoDisable),
                new ScoreNoteCommand(ScoringLocation.Amp, 1.5),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                followPathCommand(Paths.pathFromAmpToMidline));
    }
}