package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax sushiMotor = new CANSparkMax(AcquisitionConstants.sushiMotorPort, MotorType.kBrushless);
    private static CANSparkMax intakeMotor = new CANSparkMax(AcquisitionConstants.intakeMotorPort,
            MotorType.kBrushless);
    private static DigitalInput acqLaser = new DigitalInput(AcquisitionConstants.acqLaserPort);

    private Acquisition() {
        sushiMotor.restoreFactoryDefaults();
        intakeMotor.restoreFactoryDefaults();

        sushiMotor.setIdleMode(IdleMode.kBrake);
        intakeMotor.setIdleMode(IdleMode.kBrake);

        sushiMotor.burnFlash();
        intakeMotor.burnFlash();
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquire() {
        intakeMotor.set(AcquisitionConstants.intakeSpeed);
    }

    public void runSushi() {
        sushiMotor.set(AcquisitionConstants.sushiSpeed);
    }

    public void reverseMotors() {
        sushiMotor.set(AcquisitionConstants.reverseMotorSpeed);
        intakeMotor.set(AcquisitionConstants.reverseMotorSpeed);
    }

    public void stop() {
        sushiMotor.stopMotor();
        intakeMotor.stopMotor();
    }

    public boolean isNotePresent() {
        return acqLaser.get();
    }
}