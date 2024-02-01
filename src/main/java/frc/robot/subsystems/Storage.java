package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ScoringConstants;
import frc.robot.constants.StorageConstants;

public class Storage extends SubsystemBase {

    private final CANSparkMax storageMotor = new CANSparkMax(StorageConstants.motorPort,
            MotorType.kBrushless);

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

    public void run(double speed) {
        speed = MathUtil.clamp(speed, -StorageConstants.maxMotorSpeed, StorageConstants.maxMotorSpeed);
        storageMotor.set(speed);
    }

    public void stop() {
        storageMotor.stopMotor();
    }
}
