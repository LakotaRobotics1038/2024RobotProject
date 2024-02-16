package frc.robot.autons.Paths;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.autons.Auton;
import frc.robot.autons.Trajectories;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class NoteOneToAmp extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    public NoteOneToAmp(Optional<Alliance> alliance) {
        super(alliance);
        double initialPose = 3.47;
        driveTrain.zeroHeading(initialPose - 180);
        PathPlannerTrajectory trajectory = new PathPlannerTrajectory(Trajectories.noteOneToAmp(),
                driveTrain.getChassisSpeeds(),
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        Dashboard.getInstance().setTrajectory(trajectory);

        if (getInitialPose() != null) {
            driveTrain.resetOdometry(getInitialPose());
        }
        super.addCommands(
                new SequentialCommandGroup(
                        followPathCommand("Position 1 to amp")));

        this.setInitialPose(trajectory);
    }
}
