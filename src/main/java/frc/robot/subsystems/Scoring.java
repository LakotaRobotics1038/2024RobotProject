package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ScoringConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Scoring extends SubsystemBase {
    private final SparkFlex scoringMotor = new SparkFlex(ScoringConstants.scoringMotorPort, MotorType.kBrushless);
    private final RelativeEncoder scoringEncoder = scoringMotor.getEncoder();
    private double feedAmpSpeed = ScoringConstants.feedAmpSpeed;

    private static Scoring instance;

    public static Scoring getInstance() {

        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }

    public Scoring() {
        SparkFlexConfig scoringConfig = new SparkFlexConfig();

        scoringConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);

        scoringMotor.configure(scoringConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        scoringEncoder.setPosition(0);
    }

    /**
     * Get the encoder value of the scoring encoder
     */
    public double getPosition() {
        return scoringEncoder.getPosition();
    }

    /**
     * Sets the speed to feed note for amp with min value of -0.1 and max of -1
     *
     * @param speed speed to feed note for amp
     */
    public void setFeedAmpSpeed(double speed) {
        feedAmpSpeed = MathUtil.clamp(speed, -1, 0.1);
    }

    /**
     * Runs the scoring roller at the constant speed designated for getting the note
     * ready to score in amp.
     */
    public void feedForAmp() {
        scoringMotor.set(feedAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for scoring in amp.
     */
    public void scoreAmp() {
        scoringMotor.set(ScoringConstants.scoreAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for getting the note
     * ready to score in trap.
     */
    public void feedForTrap() {
        scoringMotor.set(feedAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for scoring in amp.
     */
    public void scoreTrap() {
        scoringMotor.set(ScoringConstants.scoreTrapSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for getting the note
     * ready to shoot in passer.
     */
    public void feedForPasser() {
        scoringMotor.set(ScoringConstants.feedPasserSpeed);
    }

    /**
     * Stops the scoring roller.
     */
    public void stop() {
        scoringMotor.stopMotor();
    }
}
