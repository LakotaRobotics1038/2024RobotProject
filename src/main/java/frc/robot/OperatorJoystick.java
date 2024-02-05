package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.commands.LiftDepressCommand;
import frc.robot.commands.LiftExtendCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.commands.TransitionRunCommand;
import frc.robot.constants.IOConstants;
import frc.robot.constants.StorageConstants;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.LiftDepressCommand;
import frc.robot.commands.LiftExtendCommand;
import frc.robot.commands.DropNoteCommand;
import frc.robot.commands.ScoringPositionCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.commands.PrototypeShootCommand;

public class OperatorJoystick extends XboxController1038 {
    // Singleton Setup
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

        aButton.whileTrue(new DropNoteCommand());
        xButton.onTrue(new TransitionRunCommand());
        yButton.onTrue(new AcquireCommand());
        bButton.onTrue(new StorageRunCommand());
    }
}
