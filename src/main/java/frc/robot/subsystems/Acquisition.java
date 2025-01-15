package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static SparkMax sushiMotor = new SparkMax(AcquisitionConstants.sushiMotorPort, MotorType.kBrushless);
    private static SparkMax intakeMotor = new SparkMax(AcquisitionConstants.intakeMotorPort,
            MotorType.kBrushless);
    private static DigitalInput acqLaser = new DigitalInput(AcquisitionConstants.acqLaserPort);

    private Acquisition() {
        SparkMaxConfig sushiConfig = new SparkMaxConfig();
        SparkMaxConfig intakeConfig = new SparkMaxConfig();

        sushiConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeo550Current)
                .inverted(true);
        intakeConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeo550Current)
                .inverted(false);

        sushiMotor.configure(sushiConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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
    public void runIntake() {
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
        return !acqLaser.get();
    }
}
