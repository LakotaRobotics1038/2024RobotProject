package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.OperatorJoystick;

public class SwagLights implements Subsystem {

    private Acquisition acquisition = Acquisition.getInstance();
    private Storage storage = Storage.getInstance();
    private OperatorJoystick operatorJoystick = OperatorJoystick.getInstance();

    // Enums
    public enum RobotStates {
        Enabled("A"),
        Disabled("D"),
        EmergencyStop("E");

        public final String value;

        private RobotStates(String value) {
            this.value = value;
        }
    }

    public enum OperatorStates {
        Default("X"),
        NoteSeen("N"),
        NoteAcquired("G");

        public final String value;

        private OperatorStates(String value) {
            this.value = value;
        }
    }

    // Inputs and Outputs
    private SerialPort serialPort;

    // States
    private RobotStates robotState = RobotStates.Disabled;
    private OperatorStates operatorState = OperatorStates.Default;

    // Singleton Setup
    private static SwagLights instance;

    public static SwagLights getInstance() {
        if (instance == null) {
            instance = new SwagLights();
        }
        return instance;
    }

    /**
     * Initializes the serial communication
     */
    private SwagLights() {
        serialPort = new SerialPort(9600, SerialPort.Port.kMXP);
        serialPort.enableTermination();
        System.out.println("Created new serial reader");

        new Trigger(acquisition::isNotePresent)
                .onTrue(new InstantCommand(() -> operatorState = OperatorStates.NoteAcquired));
        new Trigger(storage::noteExitingStorage)
                .onFalse(new InstantCommand(() -> operatorState = OperatorStates.Default));
        new Trigger(operatorJoystick.aButton)
                .onTrue(new InstantCommand(() -> operatorState = OperatorStates.NoteSeen))
                .onFalse(new InstantCommand(() -> operatorState = OperatorStates.Default));
    }

    @Override
    public void periodic() {
        if (this.robotState == RobotStates.Enabled) {
            setLedStates(
                    this.robotState.value,
                    this.operatorState.value);
        } else {
            setLedStates(this.robotState.value);
        }
    }

    /**
     * Write an array of strings to the serial bus as a single sting
     *
     * @param values
     */
    private void setLedStates(String... values) {
        serialPort.writeString(String.join("", values));
    }

    /**
     * Closes serial port listener
     */
    public void stopSerialPort() {
        System.out.println("Closing serial port");
        serialPort.close();
    }

    /**
     * Tells the swag lights the robot is disabled
     *
     * @param isDisabled
     */
    public void setDisabled(boolean isDisabled) {
        this.robotState = isDisabled ? RobotStates.Disabled : RobotStates.Enabled;
    }

    /**
     * Tells the swag lights the robot is e-stopped
     */
    public void setEStop() {
        this.robotState = RobotStates.EmergencyStop;
    }
}
