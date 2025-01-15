package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class ReverseTrapSequenceCommand extends SequentialCommandGroup {
    private Lift lift = Lift.getInstance();
    private final double liftTrapPosition = 10.0;

    public ReverseTrapSequenceCommand() {
        this.addCommands(
                new LiftUpCommand()
                        .alongWith(
                                new WaitUntilCommand(() -> lift.getLeftPosition() >= liftTrapPosition
                                        && lift.getRightPosition() >= liftTrapPosition)
                                        .andThen(new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground,
                                                FinishActions.NoDisable))),
                new DrawbridgeUpCommand());
    }
}
