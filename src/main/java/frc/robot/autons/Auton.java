package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.commands.FollowPathHolonomic;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Alliance alliance;

    public Auton(Optional<Alliance> alliance) {
        if (alliance.isPresent()) {
            this.alliance = alliance.get();
        }
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        this.initialPose = initialTrajectory.getInitialTargetHolonomicPose();

        // We need to invert the starting pose for the red alliance.

        if (Optional.of(alliance) == DriverStation.getAlliance()) {
            Translation2d transformedTranslation = new Translation2d(this.initialPose.getX(),
                    FieldConstants.kFieldWidth - this.initialPose.getY());
            Rotation2d transformedHeading = this.initialPose.getRotation().times(-1);

            this.initialPose = new Pose2d(transformedTranslation, transformedHeading);
        }
    }

    public Pose2d getInitialPose() {
        return initialPose;
    }

    protected Command followPathCommand(String pathName) {
        PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);

        return new FollowPathHolonomic(
                path,
                this.driveTrain::getPose, // Robot pose supplier
                this.driveTrain::getChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                this.driveTrain::applyChassisSpeeds, // Method that will drive the robot given ROBOT RELATIVE
                                                     // ChassisSpeeds
                new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your
                                                 // Constants class
                        new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                        new PIDConstants(5.0, 0.0, 0.0), // Rotation PID constants
                        4.5, // Max module speed, in m/s
                        0.4, // Drive base radius in meters. Distance from robot center to furthest module.
                        new ReplanningConfig() // Default path replanning config. See the API for the options here
                ),
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red
                    // alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    if (this.alliance != null) {
                        return this.alliance == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this.driveTrain // Reference to this subsystem to set requirements
        );
    }
}