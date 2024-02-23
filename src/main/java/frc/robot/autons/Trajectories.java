package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveTrain;

public class Trajectories {

    public static PathPlannerTrajectory getFromAmpToMidlineOverNoteTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromAmpToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromAmpToMidlineTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromAmpToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromAmpToNote1Trajectory() {
        return new PathPlannerTrajectory(Paths.pathFromAmpToNote1, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote3ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote3ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote3ToSpeakerTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote3ToSpeaker, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote2ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote2ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote2ToSpeakerTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote2ToSpeaker, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromNote1ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromNote1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition2ToSpeakerTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition2ToSpeaker, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition1ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition1ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition1ToMidlineTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition1ToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition2ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition2ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition2ToMidlineTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition2ToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition3ToMidlineTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition3ToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition3ToAmpTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition3ToAmp, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromSpeakerToNote3Trajectory() {
        return new PathPlannerTrajectory(Paths.pathFromSpeakerToNote3, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromSpeakerToNote2Trajectory() {
        return new PathPlannerTrajectory(Paths.pathFromSpeakerToNote2, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromPosition3ToSpeakerTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromPosition3ToSpeaker, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

    public static PathPlannerTrajectory getFromSpeakerToMidlineTrajectory() {
        return new PathPlannerTrajectory(Paths.pathFromSpeakerToMidline, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()));
    }

}