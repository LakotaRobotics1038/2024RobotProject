package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax frontMotor = new CANSparkMax(AcquisitionConstants.frontMotorPort, MotorType.kBrushless);
    private static CANSparkMax rearMotor = new CANSparkMax(AcquisitionConstants.rearMotorPort, MotorType.kBrushless);

    private Acquisition() {
        frontMotor.restoreFactoryDefaults();
        rearMotor.restoreFactoryDefaults();

        frontMotor.setIdleMode(IdleMode.kBrake);
        rearMotor.setIdleMode(IdleMode.kBrake);

        rearMotor.follow(frontMotor, true);

        frontMotor.burnFlash();
        rearMotor.burnFlash();
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquire(double speed) {
        speed = MathUtil.clamp(speed, AcquisitionConstants.minMotorSpeed, AcquisitionConstants.maxMotorSpeed);
        frontMotor.set(speed);
    }

    public void dispose() {
        frontMotor.set(-AcquisitionConstants.motorSpeed);
    }

    public void stop() {
        frontMotor.stopMotor();
    }
}
