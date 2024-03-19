package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.LiftDownCommand;
import frc.robot.constants.LiftConstants;
import frc.robot.constants.NeoMotorConstants;

public final class Lift extends SubsystemBase {
    private CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);
    private RelativeEncoder leftLiftEncoder = leftLiftMotor.getEncoder();
    private RelativeEncoder rightLiftEncoder = rightLiftMotor.getEncoder();
    private SparkLimitSwitch leftLimitSwitch = leftLiftMotor.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
    private SparkLimitSwitch rightLimitSwitch = rightLiftMotor
            .getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    private Servo leftRatchetServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightRatchetServo = new Servo(LiftConstants.rightServoPort);

    private static Lift instance;

    /**
     * Creates an instance of the Lift subsystem if it does not exist.
     *
     * @return An instance of the lift subsystem
     */
    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }
        return instance;
    }

    /**
     * resets the motors, sets the idle mode to coast and makes the right motor
     * follow the left. Burns these settings to the flash of each motor.
     */
    private Lift() {
        leftLiftMotor.restoreFactoryDefaults();
        rightLiftMotor.restoreFactoryDefaults();

        leftLiftMotor.setIdleMode(IdleMode.kBrake);
        rightLiftMotor.setIdleMode(IdleMode.kBrake);

        leftLimitSwitch.enableLimitSwitch(true);
        rightLimitSwitch.enableLimitSwitch(true);

        leftLiftMotor.setInverted(false);
        rightLiftMotor.setInverted(true);

        leftRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        leftLiftMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightLiftMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    /**
     * Enables the left lift ratchet (sets them to a constant maximum extension).
     */
    public void enableLeftRatchet() {
        leftRatchetServo.set(LiftConstants.leftRatchetLockPos);
    }

    /**
     * Enables the right lift ratchet (sets them to a constant maximum extension).
     */
    public void enableRightRatchet() {
        rightRatchetServo.set(LiftConstants.rightRatchetLockPos);
    }

    /**
     * Disables the left lift ratchet (sets them to a constant minimum extension).
     */
    public void disableLeftRatchet() {
        leftRatchetServo.set(LiftConstants.leftRatchetUnlockPos);
    }

    /**
     * Disables the right lift ratchet (sets them to a constant minimum extension).
     */
    public void disableRightRatchet() {
        rightRatchetServo.set(LiftConstants.rightRatchetUnlockPos);
    }

    /**
     * Determines if the left ratchet is in the unlocked position
     *
     * @return is the ratchet unlocked
     */
    public boolean leftRatchetUnlocked() {
        return leftRatchetServo.get() == LiftConstants.leftRatchetUnlockPos;
    }

    /**
     * Determines if the right ratchet is in the unlocked position
     *
     * @return is the ratchet unlocked
     */
    public boolean rightRatchetUnlocked() {
        return rightRatchetServo.get() == LiftConstants.rightRatchetUnlockPos;
    }

    /**
     * Runs the left lift motor up at a constant speed.
     */
    public void runLeftUp() {
        if (this.leftRatchetUnlocked()) {
            if (leftLiftEncoder.getPosition() < LiftConstants.maxExtension) {
                leftLiftMotor.set(LiftConstants.motorSpeed);
            } else {
                leftLiftMotor.stopMotor();
            }
        } else {
            leftLiftMotor.stopMotor();
        }
    }

    /**
     * Runs the right lift motor up at a constant speed.
     */
    public void runRightUp() {
        if (this.rightRatchetUnlocked()) {
            if (rightLiftEncoder.getPosition() < LiftConstants.maxExtension) {
                rightLiftMotor.set(LiftConstants.motorSpeed);
            } else {
                rightLiftMotor.stopMotor();
            }
        } else {
            rightLiftMotor.stopMotor();
        }
    }

    public boolean isLiftUp() {
        return rightLiftEncoder.getPosition() >= LiftConstants.maxExtension
                && leftLiftEncoder.getPosition() >= LiftConstants.maxExtension;
    }

    public boolean isRightLiftUp() {
        return rightLiftEncoder.getPosition() < LiftConstants.maxExtension;
    }

    public boolean isLeftLiftUp() {
        return leftLiftEncoder.getPosition() < LiftConstants.maxExtension;
    }

    /**
     * Runs the left lift motor down at a constant speed.
     */
    public void runLeftDown() {
        leftLiftMotor.set(LiftConstants.backwardsMotorSpeed);
    }

    /**
     * Runs the right lift motor down at a constant speed.
     */
    public void runRightDown() {
        rightLiftMotor.set(LiftConstants.backwardsMotorSpeed);
    }

    /**
     * Stops left lift motor.
     */
    public void stopLeftMotor() {
        leftLiftMotor.stopMotor();
    }

    /**
     * Stops right lift motor.
     */
    public void stopRightMotor() {
        rightLiftMotor.stopMotor();
    }

    /**
     * Determines if left limit switch is pressed
     *
     * @return is limit switch pressed
     */
    public boolean leftLowerLimitReached() {
        return leftLimitSwitch.isPressed();
    }

    /**
     * Determines if right limit switch is pressed
     *
     * @return is limit switch pressed
     */
    public boolean rightLowerLimitReached() {
        return rightLimitSwitch.isPressed();
    }

    @Override
    public void periodic() {
        if (leftLimitSwitch.isPressed() && leftLiftEncoder.getPosition() != 0) {
            leftLiftEncoder.setPosition(0);
        }
        if (rightLimitSwitch.isPressed() && rightLiftEncoder.getPosition() != 0) {
            rightLiftEncoder.setPosition(0);
        }
    }
}
