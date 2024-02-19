package frc.robot.autons;

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
}
