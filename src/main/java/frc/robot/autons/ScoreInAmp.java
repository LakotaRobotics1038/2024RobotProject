package frc.robot.autons;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.DriveVisionCommand;
import frc.robot.commands.ScoreNoteAmpCommand;

public class ScoreInAmp extends Auton {

    public ScoreInAmp(Alliance alliance) {
        super(alliance);

        PathPlannerPath pathToAmp = PathPlannerPath.fromPathFile(getName());
        super.addCommands(

                new ParallelRaceGroup(
                        new DriveVisionCommand(4)),
                new ScoreNoteAmpCommand());

    }

}
