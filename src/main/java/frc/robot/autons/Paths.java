package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static PathPlannerPath pathFromPosition1ToAmp = PathPlannerPath.fromPathFile("From position 1 to amp");

    public static PathPlannerPath pathFromTaxi3ToPos1 = PathPlannerPath.fromPathFile("Taxi Position 3 reversed");
    public static PathPlannerPath pathFromAmpToNote1 = PathPlannerPath.fromPathFile("From amp to top note");

    public static PathPlannerPath pathFromNote1ToAmp = PathPlannerPath.fromPathFile("From note 1 to amp");

    public static PathPlannerPath pathFromAmpToMidline = PathPlannerPath.fromPathFile("From amp to midline");
    public static PathPlannerPath pathFromAmpToMidlineAcquire = PathPlannerPath
            .fromPathFile("From amp to midline acquire");
    public static PathPlannerPath pathFromMidlineNoteToAmp = PathPlannerPath.fromPathFile("From midline note to amp");
    public static PathPlannerPath pathFromAmpToMidlineNote2 = PathPlannerPath
            .fromPathFile("From amp to midline acquire Note2");
    public static PathPlannerPath pathFromNote2ToTrap = PathPlannerPath.fromPathFile("From Note 2 to Mid Trap");

    public static PathPlannerPath pathFromBackPos1ToAmp = PathPlannerPath.fromPathFile("from back pos to amp");

    public static PathPlannerPath taxiPath1 = PathPlannerPath.fromPathFile("Taxi Position 1");
    public static PathPlannerPath taxiPath2 = PathPlannerPath.fromPathFile("Taxi Position 2");
    public static PathPlannerPath taxiPath3 = PathPlannerPath.fromPathFile("Taxi Position 3");
}