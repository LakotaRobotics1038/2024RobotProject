package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Scoring;
import frc.robot.constants.ScoringConstants;
import frc.robot.constants.StorageConstants;
import frc.robot.subsystems.Scoring.ElevatorSetpoints;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.LiftDepressCommand;
import frc.robot.commands.LiftExtendCommand;
import frc.robot.commands.StorageRunCommand;

public class OperatorJoystick extends XboxController1038 {
    private Scoring scoring = Scoring.getInstance();

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

        switch (this.getPOVPosition()) {
            case Up:
                scoring.runRoller(ScoringConstants.rollerSpeed);
                break;
            case Right:
                scoring.setSetpoint(ElevatorSetpoints.amp);
                break;
            case Down:
                scoring.setSetpoint(ElevatorSetpoints.ground);
                break;
            case Left:
                scoring.setSetpoint(ElevatorSetpoints.trap);
                break;
            default:
                break;
        }

        rightBumper.onTrue(new AcquireCommand(AcquisitionConstants.motorSpeed));
        rightTrigger.whileTrue(new AcquireCommand(AcquisitionConstants.reverseMotorSpeed));

    }
}
