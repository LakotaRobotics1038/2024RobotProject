package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.DriveToNoteVisionAuton;

public class DriveToNoteVision extends Auton {

    public DriveToNoteVision(Optional<Alliance> alliance) {
        super(alliance);

        this.setInitialPose(Trajectories.getFromPosition1ToNote1Trajectory());

        addCommands(
                followPathCommand(Paths.pathFromPosition1ToNote1).andThen(new DriveToNoteVisionAuton(0.2)));
    }

}
