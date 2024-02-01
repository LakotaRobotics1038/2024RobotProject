package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ScoringConstants;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax storageMotor = new CANSparkMax(StorageConstants.motorPort,
            MotorType.kBrushless);
    private static DigitalInput transitionLaser = new DigitalInput(StorageConstants.storageLaserPort);
    private static DigitalInput storageSensor = new DigitalInput(ScoringConstants.loaderLaserPort);

    private static Storage instance;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private Storage() {
        storageMotor.restoreFactoryDefaults();
        storageMotor.setInverted(true);
        storageMotor.setIdleMode(IdleMode.kBrake);
        storageMotor.burnFlash();
    }

    public boolean isNoteInTransition() {
        return transitionLaser.get();
    }

    public boolean isNoteInStorage() {
        return storageSensor.get();
    }

    public void run() {
        storageMotor.set(StorageConstants.motorSpeed);
    }

    public void stop() {
        storageMotor.stopMotor();
    }
}
