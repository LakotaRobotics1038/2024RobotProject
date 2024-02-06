package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

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

    /**
     * Creates a new instance of the Acquisition subsystem if it does not exist.
     *
     * @return An instance of the Acquisition subsystem
     */
    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    /**
     * Runs the acquisition intake motor at a constant speed.
     */
    public void acquire() {
        intakeMotor.set(AcquisitionConstants.intakeSpeed);
    }

    
    /**
     * Runs the acquisition sushi motor at a constant speed.
     */
    public void runSushi() {
        sushiMotor.set(AcquisitionConstants.sushiSpeed);
    }

    /**
     * Reverses both acquisition motors at a constant speed.
     */
    public void reverseMotors() {
        sushiMotor.set(AcquisitionConstants.reverseMotorSpeed);
        intakeMotor.set(AcquisitionConstants.reverseMotorSpeed);
    }

    /**
     * Stops the acquisition sushi motor.
     */
    public void stopSushi() {
        sushiMotor.stopMotor();
    }

    
    /**
     * Stops the acquisition intake motor.
     */
    public void stopIntake() {
        intakeMotor.stopMotor();
    }

    /**
     * Returns the output of the acquisition laser.
     *
     * @return boolean - status of the acquisition laser
     */
    public boolean isNotePresent() {
        return acqLaser.get();
    }
}
