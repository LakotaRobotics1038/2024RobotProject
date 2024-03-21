package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Dashboard;

public class TaxiPos3 extends Auton {
    public TaxiPos3(Optional<Alliance> alliance) {
        super(alliance);

        Dashboard.getInstance().setTrajectory(Trajectories.getFromPos3TaxiTrajectory());
        Dashboard.getInstance().setTrajectory(Trajectories.getFromTaxi3ToPos1Trajectory());
        this.setInitialPose(Trajectories.getFromPos3TaxiTrajectory());

        addCommands(
                followPathCommand(Paths.taxiPath3),
                new WaitCommand(0.5),
                followPathCommand(Paths.pathFromTaxi3ToPos1),
                new WaitCommand(0.5),
                followPathCommand(Paths.taxiPath3));
    }
}