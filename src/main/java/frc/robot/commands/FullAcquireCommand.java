package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class FullAcquireCommand extends SequentialCommandGroup {
    public FullAcquireCommand() {
        this.addCommands(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp),
                new AcquisitionRunCommand(),
                new TransitionRunCommand(),
                new StorageRunCommand());
    }
}
