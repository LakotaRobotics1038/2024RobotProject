package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveTrain;

public class Trajectories {
    public static Optional<PathPlannerTrajectory> getFromPos1TaxiTrajectory() {
        try {
            return Optional.of(
                    new PathPlannerTrajectory(
                            PathPlannerPath.fromPathFile(Paths.pathFromNote1ToAmp),
                            DriveTrain.getInstance().getChassisSpeeds(),
                            Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
                            AutoConstants.kRobotConfig.get()));
        } catch (Exception e) {
            DriverStation.reportError("Failed to create trajectory: " + e.getMessage(), e.getStackTrace());
            return Optional.empty();
        }
    }

    public static Optional<PathPlannerTrajectory> getFromPos3TaxiTrajectory() {
        try {
            return Optional.of(
                    new PathPlannerTrajectory(
                            PathPlannerPath.fromPathFile(Paths.taxiPath3),
                            DriveTrain.getInstance().getChassisSpeeds(),
                            Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
                            AutoConstants.kRobotConfig.get()));
        } catch (Exception e) {
            DriverStation.reportError("Failed to create trajectory: " + e.getMessage(), e.getStackTrace());
            return Optional.empty();
        }
    }

    public static Optional<PathPlannerTrajectory> getFromPosition1ToAmpTrajectory() {
        try {
            return Optional.of(
                    new PathPlannerTrajectory(
                            PathPlannerPath.fromPathFile(Paths.pathFromPosition1ToAmp),
                            DriveTrain.getInstance().getChassisSpeeds(),
                            Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
                            AutoConstants.kRobotConfig.get()));
        } catch (Exception e) {
            DriverStation.reportError("Failed to create trajectory: " + e.getMessage(), e.getStackTrace());
            return Optional.empty();
        }
    }
}