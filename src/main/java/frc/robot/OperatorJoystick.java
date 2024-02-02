package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.libraries.XboxController1038.PovPositions;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.IOConstants;
import frc.robot.constants.StorageConstants;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.LiftDepressCommand;
import frc.robot.commands.LiftExtendCommand;
import frc.robot.commands.ScoreNoteCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.StorageRunCommand;

public class OperatorJoystick extends XboxController1038 {
    // singleton setup
    private final int OperatorJoystickPort = 1;
    private XboxController1038 operatorJoystick = new XboxController1038(OperatorJoystickPort);

    private static OperatorJoystick instance;

    public static OperatorJoystick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorJoystick();
        }
        return instance;
    }

    private OperatorJoystick() {
        super(IOConstants.kOperatorControllerPort);

        aButton.whileTrue(new StorageRunCommand(StorageConstants.reverseMotorSpeed));
        bButton.whileTrue(new StorageRunCommand(StorageConstants.motorSpeed));
        xButton.onTrue(new LiftDepressCommand());
        yButton.onTrue(new LiftExtendCommand());

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Up)
                .onTrue(new ScoreNoteCommand());

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Left)
                .onTrue(new ScoringPositionCommand(ElevatorSetpoints.trap));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Down)
                .onTrue(new ScoringPositionCommand(ElevatorSetpoints.ground));

        new Trigger(() -> operatorJoystick.getPOVPosition() == PovPositions.Right)
                .onTrue(new ScoringPositionCommand(ElevatorSetpoints.trap));

        rightBumper.onTrue(new AcquireCommand(AcquisitionConstants.motorSpeed));
        rightTrigger.whileTrue(new AcquireCommand(AcquisitionConstants.reverseMotorSpeed));

    }
}
