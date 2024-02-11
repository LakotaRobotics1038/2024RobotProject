package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Optional<Alliance> alliance;

    public Auton(Optional<Alliance> alliance2) {
        this.alliance = alliance2;
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        this.initialPose = initialTrajectory.getInitialTargetHolonomicPose();

        // We need to invert the starting pose for the red alliance.
        if (this.alliance == DriverStation.getAlliance()) {
            Translation2d transformedTranslation = new Translation2d(this.initialPose.getX(),
                    FieldConstants.kFieldWidth - this.initialPose.getY());
            Rotation2d transformedHeading = this.initialPose.getRotation().times(-1);

            this.initialPose = new Pose2d(transformedTranslation, transformedHeading);
        }
    }

}