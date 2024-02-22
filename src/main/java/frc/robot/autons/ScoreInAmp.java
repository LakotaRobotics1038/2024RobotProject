package frc.robot.autons;

import java.nio.file.Path;

import java.util.Optional;
import java.util.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import frc.robot.subsystems.Vision.VisionTarget;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DriveVisionCommand;
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



                followPathCommand(Paths.pathFromPosition1ToAmp),
                new ScoreNoteAmpCommand(),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote1ToAmp),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        followPathCommand(Paths.pathFromNote1ToAmp),
                        new ScoringPositionCommand(ElevatorSetpoints.Amp)),
                new StorageRunCommand(),
                new ScoreNoteAmpCommand(),
                followPathCommand(Paths.pathFromAmpToMidline));

    }

}