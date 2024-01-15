package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Scoring;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Vision;
import frc.robot.constants.StorageConstants;
import frc.robot.constants.ScoringConstants;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;

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

        // aButton.whileTrue(new StorageRunCommand(StorageConstants.reverseMotorSpeed));
        // bButton.whileTrue(new StorageRunCommand(StorageConstants.motorSpeed));
        // xButton.onTrue(new liftDepressCommand());
        // yButton.onTrue(new liftExtendCommand());

        /*
         * switch (this.getPOVPosition()) {
         * case Up:
         * InstantCommand(new ScoreNoteCommand());
         * break;
         * case Right:
         * InstantCommand(new ScoringPositionCommand(ElevatorSetpoints.amp));
         * break;
         * case Down:
         * InstantCommand(new ScoringPositionCommand(ElevatorSetpoints.ground));
         * break;
         * case Left:
         * InstantCommand(new ScoringPositionCommand(ElevatorSetpoints.trap));
         * break;
         * default:
         * break;
         * }
         */

        // rightBumper.onTrue(AcquireCommand(ScoringConstants.motorSpeed));
        // rightTrigger.onTrue(AcquireCommand(ScoringConstants.reverseMotorSpeed));
    }
}
