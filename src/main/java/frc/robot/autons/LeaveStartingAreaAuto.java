package frc.robot.autons;

import com.pathplanner.lib.path.PathPlannerTrajectory;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Dashboard;

public class LeaveStartingAreaAuto extends Auton {
    public LeaveStartingAreaAuto(Alliance alliance, PathPlannerTrajectory trajectory) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(trajectory);

        super.addCommands(

        // this.driveTrain.getTrajectoryCommand(trajectory);
        // this.setInitialPose(trajectory);
        );
    }
}