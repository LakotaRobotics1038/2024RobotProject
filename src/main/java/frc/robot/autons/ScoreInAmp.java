package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.commands.ScoringPositionCommand;
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
                new WaitCommand(0.2),
                new ScoreNoteAmpCommand(),
                new WaitCommand(0.2),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(Paths.pathFromNote1ToAmp),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new WaitCommand(0.2),
                new AcquireCommand(),
                new WaitCommand(0.5),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(Paths.pathFromNote1ToAmp),
                        new ScoringPositionCommand(ElevatorSetpoints.Amp)),
                new WaitCommand(0.2),
                new StorageRunCommand(),
                new WaitCommand(0.2),
                new ScoreNoteAmpCommand(),
                new WaitCommand(0.2),
                AutoBuilder.followPath(Paths.pathFromAmpToMidline));
    }

}
