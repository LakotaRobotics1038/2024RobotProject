package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax transitionMotor = new CANSparkMax(StorageConstants.transitionMotorPort,
            MotorType.kBrushless);
    private final CANSparkMax leftStorage = new CANSparkMax(
            StorageConstants.leftStoragePort, MotorType.kBrushless);
    private final CANSparkMax rightStorage = new CANSparkMax(
            StorageConstants.rightStoragePort, MotorType.kBrushless);

    private final DigitalInput beginLaser = new DigitalInput(StorageConstants.beginLaserPort);
    private final DigitalInput endLaser = new DigitalInput(StorageConstants.endLaserPort);

    private static Storage instance;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private Storage() {
        transitionMotor.restoreFactoryDefaults();
        leftStorage.restoreFactoryDefaults();
        rightStorage.restoreFactoryDefaults();

        transitionMotor.setInverted(true);

        leftStorage.setIdleMode(IdleMode.kCoast);
        rightStorage.setIdleMode(IdleMode.kCoast);
        transitionMotor.setIdleMode(IdleMode.kBrake);

        rightStorage.follow(leftStorage, true);

        transitionMotor.burnFlash();
        leftStorage.burnFlash();
        rightStorage.burnFlash();
    }

    public void runTransition() {
        transitionMotor.set(StorageConstants.transitionSpeed);
    }

    public void reverseTransition() {
        transitionMotor.set(-StorageConstants.transitionSpeed);
    }

    public void runStorage() {
        leftStorage.set(StorageConstants.storageSpeed);
    }

    public void reverseStorage() {
        leftStorage.set(-StorageConstants.storageSpeed);
    }

    public void stopStorage() {
        leftStorage.stopMotor();
    }

    public void stopTransition() {
        transitionMotor.stopMotor();
    }

    public boolean noteEnteringStorage() {
        return beginLaser.get();
    }

    public boolean noteExitingStorage() {
        return endLaser.get();
    }
}
