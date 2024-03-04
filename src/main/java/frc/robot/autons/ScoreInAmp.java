package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.FullAcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.StorageRunCommand;

public class ScoreInAmp extends Auton {

    private double initialPose;

    public ScoreInAmp(Optional<Alliance> alliance) {
        super(alliance);

        this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToAmpTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());

        super.addCommands(
                AutoBuilder.followPath(Paths.pathFromPosition1ToAmp),
                // new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp,
                // FinishActions.noDisable)
                new ScoreNoteAmpCommand(3),
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                AutoBuilder.followPath(Paths.pathFromNote1ToAmp),
                                AutoBuilder.followPath(Paths.pathFromNote1ToAmp)),
                        new FullAcquireCommand()),
                // new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp,
                // FinishActions.noDisable)
                new ScoreNoteAmpCommand(3),
                AutoBuilder.followPath(Paths.pathFromAmpToMidline));
    }

}
