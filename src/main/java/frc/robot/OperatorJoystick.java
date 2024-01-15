package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Vision;

public class OperatorJoystick extends XboxController1038 {
    // Singleton Setup
    private Acquisition acquisition = Acquisition.getInstance();
    private Lift lift = Lift.getInstance();
    private Scoring scoring = Scoring.getInstance();
    private Storage storage = Storage.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();
    private Vision vision = Vision.getInstance();

    public enum ElevatorStates {
        Ground,
        Amp,
        Trap;
    }

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
    }
}
