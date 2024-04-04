package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class TrapSequenceCommand extends SequentialCommandGroup {
    private Lift lift = Lift.getInstance();
    private final double liftTrapPosition = 0.5;

    public TrapSequenceCommand() {
        this.addCommands(
                new LiftDownCommand()
                        .alongWith(
                                new SequentialCommandGroup(
                                        new DrawbridgeDownCommand(),
                                        new WaitUntilCommand(
                                                () -> lift.getLeftPosition() <= liftTrapPosition
                                                        && lift.getRightPosition() <= liftTrapPosition),
                                        new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap,
                                                FinishActions.NoDisable),
                                        new WaitUntilCommand(
                                                () -> lift.leftLowerLimitReached() && lift.rightLowerLimitReached()),
                                        new ScoreNoteCommand(ScoringLocation.Trap))));
    }
}
