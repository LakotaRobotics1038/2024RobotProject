package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;

public class NotesInAmpAuto extends Auton {

    public NotesInAmpAuto(Optional<Alliance> alliance, PathPlannerTrajectory trajectory) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(trajectory);
        NamedCommands.registerCommand("AcquireCommand", new AcquireCommand());
        NamedCommands.registerCommand("ScoreNoteAmpCommand", new ScoreNoteAmpCommand());

        super.addCommands(
                new SequentialCommandGroup(
                        new PathPlannerAuto("2 Notes in Amp")));

        this.setInitialPose(trajectory);

    }
}
