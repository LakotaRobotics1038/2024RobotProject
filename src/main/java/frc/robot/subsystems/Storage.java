package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax transitionMotor = new CANSparkMax(StorageConstants.transitionMotorPort,
            MotorType.kBrushed);
    private final CANSparkMax leftStorageMotor = new CANSparkMax(
            StorageConstants.leftStoragePort, MotorType.kBrushless);
    private final CANSparkMax rightStorageMotor = new CANSparkMax(
            StorageConstants.rightStoragePort, MotorType.kBrushless);

    private final DigitalInput enterStorageMotor = new DigitalInput(StorageConstants.enterStorageMotorPort);
    private final DigitalInput exitStorageMotor = new DigitalInput(StorageConstants.exitStorageMotorPort);

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
        leftStorageMotor.restoreFactoryDefaults();
        rightStorageMotor.restoreFactoryDefaults();

        leftStorageMotor.setIdleMode(IdleMode.kCoast);
        rightStorageMotor.setIdleMode(IdleMode.kCoast);
        transitionMotor.setIdleMode(IdleMode.kBrake);

        rightStorageMotor.follow(leftStorageMotor, true);

        transitionMotor.burnFlash();
        leftStorageMotor.burnFlash();
        rightStorageMotor.burnFlash();
    }

    public void runTransition() {
        transitionMotor.set(StorageConstants.transitionSpeed);
    }

    public void reverseTransition() {
        transitionMotor.set(StorageConstants.reverseTransitionSpeed);
    }

    public void runStorage() {
        leftStorageMotor.set(StorageConstants.storageSpeed);
    }

    public void reverseStorage() {
        leftStorageMotor.set(StorageConstants.reverseStorageSpeed);
    }

    public void stopStorage() {
        leftStorageMotor.stopMotor();
    }

    public void stopTransition() {
        transitionMotor.stopMotor();
    }

    public boolean noteEnteringStorage() {
        return enterStorageMotor.get();
    }

    public boolean noteExitingStorage() {
        return exitStorageMotor.get();
    }
}
