package frc.robot.autons;

import java.nio.file.Path;

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

    public static PathPlannerPath pathFromAmpToBack = PathPlannerPath.fromPathFile("from amp to back");

    public static PathPlannerPath taxiPath1 = PathPlannerPath.fromPathFile("Taxi Position 1");
    public static PathPlannerPath taxiPath2 = PathPlannerPath.fromPathFile("Taxi Position 2");
    public static PathPlannerPath taxiPath3 = PathPlannerPath.fromPathFile("Taxi Position 3");

    public static PathPlannerPath pathFromSourceToBottomNote = PathPlannerPath
            .fromPathFile("From Source to Bottom Note");
    public static PathPlannerPath pathFromBottomNoteTo2ndBottomNote = PathPlannerPath
            .fromPathFile("From Midline bottom note to 2nd bottom note");
    public static PathPlannerPath pathFrom2ndBottomNoteToMiddleNote = PathPlannerPath
            .fromPathFile("Midline 2nd to bottom note to middle");
    public static PathPlannerPath pathFromMiddleNoteTo2ndNote = PathPlannerPath
            .fromPathFile("Midline Middle note to 2nd note");

    public static PathPlannerPath pathFromPos1ToTopNote = PathPlannerPath
            .fromPathFile("From Position 1 to Top Midline note");
    public static PathPlannerPath pathFromTopNoteTo2ndTopNote = PathPlannerPath
            .fromPathFile("From top note to 2nd top note");
    public static PathPlannerPath pathFrom2ndTopNoteToMiddleNote = PathPlannerPath
            .fromPathFile("midline 2nd top note to middle");
    public static PathPlannerPath pathFromMiddleNoteTo2ndBottomNote = PathPlannerPath
            .fromPathFile("From midline middle note to 2nd bottom");
}