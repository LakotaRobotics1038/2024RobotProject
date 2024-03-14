package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class TrapSequenceCommand extends SequentialCommandGroup {

    public void TrapSequenceCommand() {
        this.addCommands(
                new ParallelCommandGroup(
                        new DrawbridgeDownCommand(),
                        new SequentialCommandGroup(
                                new WaitCommand(3),
                                new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap, FinishActions.NoDisable),
                                new ScoreNoteCommand(ScoringLocation.Trap, 3),
                                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground,
                                        FinishActions.NoDisable))));
    }
}
