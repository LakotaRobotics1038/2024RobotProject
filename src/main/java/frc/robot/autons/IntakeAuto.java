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

public class IntakeAuto extends Auton {

    public IntakeAuto(Optional<Alliance> alliance) {
        super(alliance);
        // try .concatenate a return and final trajectory later

        super.addCommands(
                new AcquireCommand());
    }

}