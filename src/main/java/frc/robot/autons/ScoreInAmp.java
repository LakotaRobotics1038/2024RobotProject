package frc.robot.autons;

import java.nio.file.Path;

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

    public ScoreInAmp(Alliance alliance) {
        super(alliance);

        super.addCommands(

                new ParallelRaceGroup(
                        new DriveVisionCommand(VisionTarget.APR1),
                        AutoBuilder.followPath(Paths.pathFromPosition1ToAmp)),
                new ScoreNoteAmpCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                new DriveVisionCommand(VisionTarget.NOTES),
                                AutoBuilder.followPath(Paths.pathFromAmpToNote1)),
                        new ScoringPositionCommand(ElevatorSetpoints.Ground)),
                new AcquireCommand(),
                new ParallelCommandGroup(
                        new ParallelRaceGroup(
                                new DriveVisionCommand(VisionTarget.APR1),
                                AutoBuilder.followPath(Paths.pathFromNote1ToAmp)),
                        new ScoringPositionCommand(ElevatorSetpoints.Amp)),
                new StorageRunCommand(),
                new ScoreNoteAmpCommand(),
                AutoBuilder.followPath(Paths.pathFromAmpToMidline));

    }

}
