package frc.robot.autons;

import java.util.ArrayList;
import java.util.List;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory.State;

public class Trajectories {
    public static PathPlannerPath posOneToAmp() {
        return PathPlannerPath.fromPathFile("Position 1 to amp");
    }

    public static PathPlannerPath ampToNoteOne() {
        return PathPlannerPath.fromPathFile("amp to note 1");
    }

    public static PathPlannerPath noteOneToAmp() {
        return PathPlannerPath.fromPathFile("note 1 to amp");
    }

}
