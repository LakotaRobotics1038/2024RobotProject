package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkMaxConfigAccessor;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final SparkMax transitionMotor = new SparkMax(
            StorageConstants.transitionMotorPort,
            MotorType.kBrushless);
    private final SparkMax storageMotor = new SparkMax(
            StorageConstants.storagePort,
            MotorType.kBrushless);

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
        SparkMaxConfig transitionConfig = new SparkMaxConfig();
        SparkMaxConfig storageConfig = new SparkMaxConfig();

        transitionConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        storageConfig
                .idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);

        transitionMotor.configure(transitionConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        storageMotor.configure(storageConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
