package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static PathPlannerPath pathFromPosition1ToAmp = PathPlannerPath.fromPathFile("From position 1 to amp");

    public static PathPlannerPath pathFromAmpToNote1 = PathPlannerPath.fromPathFile("From amp to top note");

    public static PathPlannerPath pathFromNote1ToAmp = PathPlannerPath.fromPathFile("From note 1 to amp");

    public static PathPlannerPath pathFromAmpToMidline = PathPlannerPath.fromPathFile("From amp to midline");

    public static PathPlannerPath taxiPath1 = PathPlannerPath.fromPathFile("Taxi Position 1");
    public static PathPlannerPath taxiPath2 = PathPlannerPath.fromPathFile("Taxi Position 2");
    public static PathPlannerPath taxiPath3 = PathPlannerPath.fromPathFile("Taxi Position 3");
}