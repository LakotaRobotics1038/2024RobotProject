package frc.robot.autons;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Alliance alliance;

    public Auton(Alliance alliance) {
        this.alliance = alliance;
    }
}