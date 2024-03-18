package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax transitionMotor = new CANSparkMax(StorageConstants.transitionMotorPort,
            MotorType.kBrushless);
    private final CANSparkMax storageMotor = new CANSparkMax(
            StorageConstants.storagePort, MotorType.kBrushless);

    private final DigitalInput enterStorageLaser = new DigitalInput(StorageConstants.enterStorageLaserPort);
    private final DigitalInput exitStorageLaser = new DigitalInput(StorageConstants.exitStorageLaserPort);

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

    private Storage() {
        transitionMotor.restoreFactoryDefaults();
        storageMotor.restoreFactoryDefaults();

        storageMotor.setIdleMode(IdleMode.kBrake);
        transitionMotor.setIdleMode(IdleMode.kBrake);

        storageMotor.setInverted(false);
        transitionMotor.setInverted(false);

        storageMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        transitionMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);

        transitionMotor.burnFlash();
        storageMotor.burnFlash();
    }

    public void runTransition() {
        transitionMotor.set(StorageConstants.transitionSpeed);
    }

    public void reverseTransition() {
        transitionMotor.set(StorageConstants.reverseTransitionSpeed);
    }

    public void runStorage() {
        storageMotor.set(StorageConstants.storageSpeed);
    }

    public void reverseStorage() {
        storageMotor.set(StorageConstants.reverseStorageSpeed);
    }

    public void stopStorage() {
        storageMotor.stopMotor();
    }

    public void stopTransition() {
        transitionMotor.stopMotor();
    }

    public boolean noteEnteringStorage() {
        return !enterStorageLaser.get();
    }

    public boolean noteExitingStorage() {
        return !exitStorageLaser.get();
    }
}
