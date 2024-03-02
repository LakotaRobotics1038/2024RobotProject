package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.IOConstants;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.ReverseStorageCommand;
import frc.robot.commands.UnacquireCommand;
import frc.robot.commands.ScoringElevatorPositionCommand.FinishActions;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.FullAcquireCommand;

public class OperatorJoystick extends XboxController1038 {
    // Singleton Setup
    private static OperatorJoystick instance;
    private final int OperatorJoystickPort = 1;
    private XboxController1038 operatorJoystick = new XboxController1038(OperatorJoystickPort);

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
        aButton.whileTrue(new AcquisitionRunCommand());
        bButton.whileTrue(new ReverseStorageCommand());
        xButton.whileTrue(new UnacquireCommand());

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Left)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Trap, FinishActions.NoFinish));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Down)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Ground, FinishActions.NoFinish));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Right)
                .toggleOnTrue(new ScoringElevatorPositionCommand(ElevatorSetpoints.Amp, FinishActions.NoFinish));

        rightTrigger.whileTrue(new ScoreNoteAmpCommand());
        leftTrigger.whileTrue(new ShootNoteCommand());

    }
}
