package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax rearMotor = new CANSparkMax(AcquisitionConstants.frontMotorID, MotorType.kBrushless);
    private static CANSparkMax frontMotor = new CANSparkMax(AcquisitionConstants.frontMotorID, MotorType.kBrushless);

    private Acquisition() {
        rearMotor.restoreFactoryDefaults();
        frontMotor.restoreFactoryDefaults();

        rearMotor.setIdleMode(IdleMode.kCoast);
        frontMotor.setIdleMode(IdleMode.kCoast);

        rearMotor.follow(frontMotor);

        rearMotor.burnFlash();
        frontMotor.burnFlash();
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void runRear() {
        rearMotor.set(AcquisitionConstants.rearMotorSpeed);
    }

    public void runFront() {
        frontMotor.set(AcquisitionConstants.frontMotorSpeed);
    }

    public void stopRear() {
        rearMotor.stopMotor();
    }

    public void stopFront() {
        frontMotor.stopMotor();
    }
}
