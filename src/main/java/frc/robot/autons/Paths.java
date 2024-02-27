package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static PathPlannerPath pathFromPosition1ToAmp = PathPlannerPath.fromPathFile("From position 1 to amp");
    public static PathPlannerPath pathFromPosition2ToAmp = PathPlannerPath.fromPathFile("From position 2 to the amp");
    public static PathPlannerPath pathFromPosition3ToAmp = PathPlannerPath.fromPathFile("From position 3 to the amp");

    public static PathPlannerPath pathFromAmpToNote1 = PathPlannerPath.fromPathFile("From amp to top note");

    public static PathPlannerPath pathFromNote1ToAmp = PathPlannerPath.fromPathFile("From note 1 to amp");
    public static PathPlannerPath pathFromNote2ToAmp = PathPlannerPath.fromPathFile("From middle note to amp");
    public static PathPlannerPath pathFromNote3ToAmp = PathPlannerPath.fromPathFile("From bottom note to amp");

    public static PathPlannerPath pathFromAmpToMidline = PathPlannerPath.fromPathFile("From amp to midline");
    public static PathPlannerPath pathFromAmpRollingOverNoteToMidline = PathPlannerPath
            .fromPathFile("From amp to midline rolling over note");
    public static PathPlannerPath pathFromPosition1ToMidline = PathPlannerPath
            .fromPathFile("From position 1 to the midline");
    public static PathPlannerPath pathFromPosition2ToMidline = PathPlannerPath
            .fromPathFile("From position 2 to the midline");
    public static PathPlannerPath pathFromPosition3ToMidline = PathPlannerPath
            .fromPathFile("From position 3 to midline");

    public static PathPlannerPath pathFromNote2ToSpeaker = PathPlannerPath.fromPathFile("From middle note to speaker");
    public static PathPlannerPath pathFromNote3ToSpeaker = PathPlannerPath.fromPathFile("From bottom note to speaker");

    public static PathPlannerPath pathFromPosition2ToSpeaker = PathPlannerPath
            .fromPathFile("From poition 2 to speaker");
    public static PathPlannerPath pathFromPosition3ToSpeaker = PathPlannerPath
            .fromPathFile("From position 3 to speaker");

    public static PathPlannerPath pathFromSpeakerToNote2 = PathPlannerPath.fromPathFile("From speaker to middle note");
    public static PathPlannerPath pathFromSpeakerToNote3 = PathPlannerPath.fromPathFile("From speaker to bottom note");

    public static PathPlannerPath pathFromSpeakerToMidline = PathPlannerPath.fromPathFile("From speaker to midline");

    public static PathPlannerPath rotate() {
        return PathPlannerPath.fromPathFile("rotate");
    }
}