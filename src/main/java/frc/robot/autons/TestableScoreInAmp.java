package frc.robot.autons;

import java.nio.file.Path;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

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

public class TestableScoreInAmp extends Auton {

    public TestableScoreInAmp(Optional<Alliance> alliance) {
        super(alliance);

        PathPlannerPath pathToAmpFromStart = PathPlannerPath.fromPathFile("From position 1 to amp");
        PathPlannerPath pathToNote1FromAmp = PathPlannerPath.fromPathFile("From amp to top note");
        PathPlannerPath pathToAmpFromNote1 = PathPlannerPath.fromPathFile("From note 1 to amp");
        PathPlannerPath pathToMidlineFromAmp = PathPlannerPath.fromPathFile("From amp to midline");

        super.addCommands(
                AutoBuilder.followPath(pathToAmpFromStart),
                new ScoreNoteAmpCommand(),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(pathToNote1FromAmp),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        AutoBuilder.followPath(pathToAmpFromNote1),
                        new StorageRunCommand()),
                new ScoringPositionCommand(ElevatorSetpoints.Amp),
                new ScoreNoteAmpCommand(),
                AutoBuilder.followPath(pathToMidlineFromAmp));

    }

}
