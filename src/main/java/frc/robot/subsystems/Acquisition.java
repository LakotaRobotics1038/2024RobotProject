package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax rearMotor = new CANSparkMax(AcquisitionConstants.frontMotorPort, MotorType.kBrushless);
    private static CANSparkMax frontMotor = new CANSparkMax(AcquisitionConstants.frontMotorPort, MotorType.kBrushless);

    private Acquisition() {
        rearMotor.restoreFactoryDefaults();
        frontMotor.restoreFactoryDefaults();

        rearMotor.setIdleMode(IdleMode.kBrake);

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

    public void acquire(double speed) {
        frontMotor.set(speed);
    }

    public void dispose() {
        frontMotor.set(-AcquisitionConstants.motorSpeed);
    }

    public void stop() {
        frontMotor.stopMotor();
    }
}
