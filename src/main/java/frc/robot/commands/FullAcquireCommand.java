package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class FullAcquireCommand extends Command {
    public void fullAcquireCommand() {
        new SequentialCommandGroup(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp),
                new AcquisitionRunCommand(),
                new TransitionRunCommand(),
                new StorageRunCommand());
    }
}
