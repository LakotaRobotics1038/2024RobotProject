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

public class ScoreInAmp extends Auton {

    public ScoreInAmp(Optional<Alliance> alliance) {
        super(alliance);

        PathPlannerPath pathToAmpFromStart = PathPlannerPath.fromPathFile("From position 1 to amp");
        PathPlannerPath pathToNote1FromAmp = PathPlannerPath.fromPathFile("From amp to top note");
        PathPlannerPath pathToAmpFromNote1 = PathPlannerPath.fromPathFile("From note 1 to amp");
        PathPlannerPath pathToMidlineFromAmp = PathPlannerPath.fromPathFile("From amp to midline");

        super.addCommands(

                new ParallelRaceGroup(
                        new DriveVisionCommand(VisionTarget.APT4),
                        AutoBuilder.followPath(pathToAmpFromStart)),
                new ScoreNoteAmpCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                new DriveVisionCommand(VisionTarget.NOTES),
                                AutoBuilder.followPath(pathToNote1FromAmp)),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                new DriveVisionCommand(VisionTarget.APT4),
                                AutoBuilder.followPath(pathToAmpFromNote1)),
                        new ScoringPositionCommand(ElevatorSetpoints.Amp)),
                new StorageRunCommand(),
                new ScoreNoteAmpCommand(),
                AutoBuilder.followPath(pathToMidlineFromAmp));

    }

}
