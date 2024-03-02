package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import frc.robot.subsystems.Dashboard;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ScoreInAmp extends Auton {

    private double initialPose;

    public ScoreInAmp(Optional<Alliance> alliance) {
        super(alliance);

        // this.resetGyro(initialPose);
        Dashboard.getInstance().setTrajectory(Trajectories.getFromPosition1ToAmpTrajectory());
        this.setInitialPose(Trajectories.getFromPosition1ToAmpTrajectory());
        // try .concatenate a return and final trajectory later

        super.addCommands(
                followPathCommand(Paths.pathFromPosition1ToAmp),
                // new WaitCommand(0.2),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp),
                new ScoreNoteAmpCommand(3),
                // new WaitCommand(0.2),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote1ToAmp),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground)),
                // try stop robot command later
                // new WaitCommand(0.2),
                new AcquireCommand(),
                // new WaitCommand(0.5),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote1ToAmp),
                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp)),
                new StorageRunCommand(),
                // new WaitCommand(0.2),
                new ScoreNoteAmpCommand(3),
                // new WaitCommand(0.2),
                followPathCommand(Paths.pathFromAmpToMidline));
    }

}