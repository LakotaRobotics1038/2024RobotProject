package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class FullAcquireCommand extends SequentialCommandGroup {
    public FullAcquireCommand() {
        this.addCommands(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                new AcquisitionRunCommand(),
                new TransitionRunCommand(),
                new StorageRunCommand());
    }
}
