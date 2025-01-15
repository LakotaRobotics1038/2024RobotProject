package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.PasserConstants;

public class Passer extends SubsystemBase {
    private final SparkFlex passerMotor = new SparkFlex(PasserConstants.passerMotorPort, MotorType.kBrushless);
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
        SparkFlexConfig passerConfig = new SparkFlexConfig();

        passerConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(true);

        passerMotor.configure(passerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        passerEncoder.setPosition(0);
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
