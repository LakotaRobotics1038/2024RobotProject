package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.LiftConstants;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class TrapSequenceCommand extends SequentialCommandGroup {
    private Lift lift = Lift.getInstance();
    private BooleanSupplier firstSetpoint = () -> lift.getLeftHeight() >= LiftConstants.firstLiftSetpoint
            && lift.getRightHeight() >= LiftConstants.firstLiftSetpoint;
    private BooleanSupplier secondSetpoint = () -> lift.getLeftHeight() >= LiftConstants.minExtension
            && lift.getRightHeight() >= LiftConstants.minExtension;

    public TrapSequenceCommand() {
        this.addCommands(
                new ParallelCommandGroup(
                        new LiftUpCommand(),
                        new SequentialCommandGroup(
                                new DrawbridgeDownCommand(),
                                new WaitUntilCommand(firstSetpoint),
                                new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap, FinishActions.NoDisable),
                                new WaitUntilCommand(secondSetpoint),
                                new ScoreNoteCommand(ScoringLocation.Trap, 3),
                                new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground,
                                        FinishActions.NoDisable))));
    }
}
