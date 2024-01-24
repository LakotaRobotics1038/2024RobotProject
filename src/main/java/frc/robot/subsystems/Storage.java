package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax storageMotor = new CANSparkMax(StorageConstants.motorPort,
            MotorType.kBrushless);

    private static Storage instance;

    /**
     * Creates an instance of the Storage subsystem if it does not already exist.
     *
     * @return An instance of the Storage subsystem.
     */
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /**
     * Resets the storage motor, inverts it, sets its idle mode to brake, and burns
     * the settings to flash.
     */
    private Storage() {
        storageMotor.restoreFactoryDefaults();
        storageMotor.setInverted(true);
        storageMotor.setIdleMode(IdleMode.kBrake);
        storageMotor.burnFlash();
    }

    /**
     * Runs the Storage motor at a constant speed.
     */
    public void run() {
        storageMotor.set(StorageConstants.motorSpeed);
    }

    /**
     * Stops the Storage motor.
     */
    public void stop() {
        storageMotor.stopMotor();
    }
}
