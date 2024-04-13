package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveTrain;

public class Trajectories {
    public static PathPlannerTrajectory getFromAmpToNote1Trajectory() {
        return new PathPlannerTrajectory(Paths.pathFromAmpToNote1, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromTaxi3ToPos1Trajectory() {
        return new PathPlannerTrajectory(Paths.pathFromTaxi3ToPos1, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPos1TaxiTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPos2TaxiTrajectory() {
        return new PathPlannerTrajectory(Paths.taxiPath2, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPos3TaxiTrajectory() {
        return new PathPlannerTrajectory(Paths.taxiPath3, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote1ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition1ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromSouceToBottomMidlineNote() {
        return new PathPlannerTrajectory(Paths.pathFromSourceToBottomNote, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }
}