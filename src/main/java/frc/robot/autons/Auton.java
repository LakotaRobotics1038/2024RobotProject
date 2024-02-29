package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Optional<Alliance> alliance;

    public Auton(Optional<Alliance> alliance2) {
        this.alliance = alliance2;
    }
}