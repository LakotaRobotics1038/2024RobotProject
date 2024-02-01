package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax driveMotor = new CANSparkMax(AcquisitionConstants.motorPort, MotorType.kBrushless);
    private static DigitalInput acqLaser = new DigitalInput(AcquisitionConstants.acqLaserPort);

    private Acquisition() {
        driveMotor.restoreFactoryDefaults();

        driveMotor.setIdleMode(IdleMode.kBrake);

        driveMotor.burnFlash();
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public boolean isNotePresent() {
        return acqLaser.get();
    }

    public void acquire(double speed) {
        speed = MathUtil.clamp(speed, AcquisitionConstants.minMotorSpeed, AcquisitionConstants.maxMotorSpeed);
        driveMotor.set(speed);
    }

    public void dispose() {
        driveMotor.set(-AcquisitionConstants.motorSpeed);
    }

    public void stop() {
        driveMotor.stopMotor();
    }
}
