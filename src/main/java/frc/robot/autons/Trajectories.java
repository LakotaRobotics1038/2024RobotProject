package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

public class Trajectories {
    public static PathPlannerPath NotesInAmp() {
        return PathPlannerPath.fromPathFile("2 Notes in Amp");
    }
}
