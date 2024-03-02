package frc.robot.autons;

import java.nio.file.Path;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static PathPlannerPath posOneToAmp() {
        return PathPlannerPath.fromPathFile("Position 1 to amp");
    }

    public static PathPlannerPath ampToNoteOne() {
        return PathPlannerPath.fromPathFile("amp to note 1");
    }

    public static PathPlannerPath noteOneToAmp() {
        return PathPlannerPath.fromPathFile("note 1 to amp");
    }

    public static PathPlannerPath rotate() {
        return PathPlannerPath.fromPathFile("rotate");
    }

    public static PathPlannerPath driveForward() {
        return PathPlannerPath.fromPathFile("DriveForward");
    }

    public static PathPlannerPath strafeLeft() {
        return PathPlannerPath.fromPathFile("StrafeLeft");
    }

    public static PathPlannerPath strafeRight() {
        return PathPlannerPath.fromPathFile("StrafeRight");
    }

    public static PathPlannerPath turnLeft() {
        return PathPlannerPath.fromPathFile("TurnLeft");
    }

    public static PathPlannerPath turnRight() {
        return PathPlannerPath.fromPathFile("TurnRight");
    }

    public static PathPlannerPath moveForwardTurnLeft() {
        return PathPlannerPath.fromPathFile("MoveForwardTurnLeft");
    }

    public static PathPlannerPath splinePath() {
        return PathPlannerPath.fromPathFile("SplinePath");
    }
}
