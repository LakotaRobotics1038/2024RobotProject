package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.commands.StorageRunCommand;
//import frc.robot.commands.TransitionRunCommand;
import frc.robot.constants.IOConstants;

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

        yButton.whileTrue(new AcquireCommand());
        // xButton.whileTrue(new TransitionRunCommand());
        bButton.whileTrue(new StorageRunCommand());
        aButton.whileTrue(new ScoreNoteAmpCommand());
    }
}
