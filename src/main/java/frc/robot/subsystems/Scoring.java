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
    private final CANSparkFlex rollerMotor = new CANSparkFlex(ScoringConstants.rollerMotorPort, MotorType.kBrushless);
    private final RelativeEncoder rollerEncoder = rollerMotor.getEncoder();
    private double feedAmpSpeed = ScoringConstants.feedAmpSpeed;

    private static Scoring instance;

    public static Scoring getInstance() {

        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }

    public Scoring() {
        rollerMotor.restoreFactoryDefaults();

        rollerMotor.setIdleMode(IdleMode.kBrake);
        rollerMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);
        rollerMotor.setInverted(false);

        rollerMotor.burnFlash();
        rollerEncoder.setPosition(0);
    }

    /**
     * Get the encoder value of the roller encoder
     */
    public double getPosition() {
        return rollerEncoder.getPosition();
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
        rollerMotor.set(feedAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for scoring in amp.
     */
    public void scoreAmp() {
        rollerMotor.set(ScoringConstants.scoreAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for getting the note
     * ready to score in trap.
     */
    public void feedForTrap() {
        rollerMotor.set(feedAmpSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for scoring in amp.
     */
    public void scoreTrap() {
        rollerMotor.set(ScoringConstants.scoreTrapSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for getting the note
     * ready to shoot in passer.
     */
    public void scorePasser() {
        rollerMotor.set(ScoringConstants.scorePasserSpeed);
    }

    /**
     * Stops the scoring roller.
     */
    public void stopRoller() {
        rollerMotor.stopMotor();
    }
}
