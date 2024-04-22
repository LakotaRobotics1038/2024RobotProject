package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class FunctionTestingCommand extends SequentialCommandGroup {
    public FunctionTestingCommand() {
        this.addCommands(
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground),
                new AcquireCommand(5),
                new TransitionRunCommand(),
                new StorageRunCommand(),
                new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp),
                new ScoreNoteCommand(ScoringLocation.Amp, 2),
                new DrawbridgeDownCommand(),
                new LiftUpCommand(),
                new DrawbridgeUpCommand(),
                new LiftDownCommand(),
                new ShootPasserCommand(5));
    }
}
