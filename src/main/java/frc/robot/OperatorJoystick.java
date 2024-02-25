package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.IOConstants;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.FullAcquireSequenceCommand;
import frc.robot.commands.ReverseStorageCommand;
import frc.robot.commands.UnacquireCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

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

        aButton.whileTrue(new FullAcquireSequenceCommand());
        bButton.whileTrue(new ReverseStorageCommand());
        xButton.whileTrue(new UnacquireCommand());

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Left)
                .toggleOnTrue(new ScoringPositionCommand(ElevatorSetpoints.Trap));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Down)
                .toggleOnTrue(new ScoringPositionCommand(ElevatorSetpoints.Ground));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Right)
                .toggleOnTrue(new ScoringPositionCommand(ElevatorSetpoints.Amp));

        rightTrigger.whileTrue(new ScoreNoteAmpCommand());
        leftTrigger.whileTrue(new ShootNoteCommand());

    }
}
