package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

public class Trajectories {
    public static PathPlannerPath NotesInAmp() {
        return PathPlannerPath.fromPathFile("Position 1 to amp");
    }
}
