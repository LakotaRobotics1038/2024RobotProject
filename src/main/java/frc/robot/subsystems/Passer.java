package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.PasserConstants;

public class Passer extends SubsystemBase {
    private final CANSparkMax passerMotor = new CANSparkMax(
            PasserConstants.passerMotorPort, MotorType.kBrushless);
    private final RelativeEncoder passerEncoder = passerMotor.getEncoder();

    private static Passer instance;

    /**
     * Creates an instance of the Passer subsystem if it does not already exist.
     *
     * @return An instance of the Passer subsystem.
     */
    public static Passer getInstance() {
        if (instance == null) {
            instance = new Passer();
        }
        return instance;
    }

    private Passer() {
        passerMotor.restoreFactoryDefaults();

        passerMotor.setIdleMode(IdleMode.kBrake);
        passerMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);
        passerMotor.setInverted(false);

        passerMotor.burnFlash();
        passerEncoder.setPosition(0);
    }

    /**
     * Get the encoder value of the passer encoder
     */
    public double getPosition() {
        return passerEncoder.getPosition();
    }

    /**
     * Runs the passer at a constant speed
     */
    public void shoot() {
        passerMotor.set(PasserConstants.shooterSpeed);
    }

    /**
     * Stops the passer
     */
    public void stop() {
        passerMotor.stopMotor();
    }
}
