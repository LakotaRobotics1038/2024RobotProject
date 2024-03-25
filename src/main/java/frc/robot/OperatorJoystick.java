package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.IOConstants;
import frc.robot.constants.ScoringConstants.ScoringLocation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.ReverseStorageCommand;
import frc.robot.commands.UnacquireCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootPasserCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.DrawbridgeDownCommand;
import frc.robot.commands.DrawbridgeUpCommand;
import frc.robot.commands.FeedNoteCommand;
import frc.robot.commands.FeedNoteFineAdjCommand;

public class OperatorJoystick extends XboxController1038 {
    // Singleton Setup
    private static OperatorJoystick instance;
    private final int OperatorJoystickPort = 1;
    private XboxController1038 operatorJoystick = new XboxController1038(OperatorJoystickPort);
    private ScoringElevator scoringElevator = ScoringElevator.getInstance();
    private Storage storage = Storage.getInstance();
    private boolean scoringElevatorLock = true;

    public static OperatorJoystick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorJoystick();
        }
        return instance;
    }

    private OperatorJoystick() {
        super(IOConstants.kOperatorControllerPort);

        // aButton.whileTrue(new FullAcquireCommand());
        aButton
                .whileTrue(new AcquisitionRunCommand())
                .onTrue(new InstantCommand(() -> scoringElevatorLock = true));
        bButton.whileTrue(new ReverseStorageCommand());
        xButton.whileTrue(new UnacquireCommand());
        yButton.onTrue(new FeedNoteFineAdjCommand());

        new Trigger(storage::noteExitingStorage)
                .whileTrue(new RunCommand(() -> scoringElevatorLock = false));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Down)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground, FinishActions.NoFinish));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Left)
                .and(() -> !scoringElevatorLock)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap, FinishActions.NoFinish));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Right)
                .and(() -> !scoringElevatorLock)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoFinish));
        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Up)
                .and(() -> scoringElevatorLock)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Passer, FinishActions.NoFinish));

        // Amp buttons
        rightTrigger
                .and(() -> scoringElevator.getSetpoint() == ElevatorSetpoints.Amp.value)
                .whileTrue(new ScoreNoteCommand(ScoringLocation.Amp));
        leftTrigger
                .and(() -> scoringElevator.getSetpoint() == ElevatorSetpoints.Amp.value)
                .whileTrue(new FeedNoteCommand(ScoringLocation.Amp));

        // Trap buttons
        rightTrigger
                .and(() -> scoringElevator.getSetpoint() == ElevatorSetpoints.Trap.value)
                .whileTrue(new ScoreNoteCommand(ScoringLocation.Trap));
        leftTrigger
                .and(() -> scoringElevator.getSetpoint() == ElevatorSetpoints.Trap.value)
                .whileTrue(new FeedNoteCommand(ScoringLocation.Trap));

        // Passer button
        leftTrigger
                .and(() -> scoringElevator.getSetpoint() == ElevatorSetpoints.Passer.value)
                .whileTrue(new ShootPasserCommand());

        leftBumper.onTrue(new DrawbridgeUpCommand());
        rightBumper.onTrue(new DrawbridgeDownCommand());
    }
}
