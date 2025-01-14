package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveTrain;

public class Trajectories {
    public static PathPlannerTrajectory getFromPos1TaxiTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPos3TaxiTrajectory() {
        return new PathPlannerTrajectory(Paths.taxiPath3, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition1ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }
}