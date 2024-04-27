package frc.robot.autons;

import java.util.Optional;

import frc.robot.subsystems.Acquisition;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootPasserCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ShootAndSit extends Auton {

    private Acquisition acquisition = Acquisition.getInstance();

    public ShootAndSit(Optional<Alliance> alliance) {
        super(alliance);

        super.addCommands(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoDisable),
                new ShootPasserCommand(2.0),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground));
    }
}