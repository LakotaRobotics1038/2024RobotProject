package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;

import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ScoringConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Scoring extends SubsystemBase {
    private final CANSparkFlex scoringMotor = new CANSparkFlex(ScoringConstants.scoringMotorPort, MotorType.kBrushless);
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
        scoringMotor.restoreFactoryDefaults();

        scoringMotor.setIdleMode(IdleMode.kBrake);
        scoringMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);
        scoringMotor.setInverted(false);

        scoringMotor.burnFlash();
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
