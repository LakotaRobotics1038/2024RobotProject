package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.constants.LiftConstants;

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

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    /**
     *
     * Enables the lift ratchets (sets them to a constant maximum extension).
     */
    public void enableRatchets() {
        leftRatchetServo.set(LiftConstants.leftRatchetLockPos);
        rightRatchetServo.set(LiftConstants.rightRatchetLockPos);
    }

    /**
     * Disables the lift ratchets (sets them to a constant minimum extension).
     */
    public void disableRatchets() {
        leftRatchetServo.set(LiftConstants.leftRatchetUnlockPos);
        rightRatchetServo.set(LiftConstants.rightRatchetUnlockPos);
    }

    /**
     * Determines if the ratchets are both in the unlocked position
     *
     * @return are the ratchets unlocked
     */
    public boolean ratchetsUnlocked() {
        return leftRatchetServo.get() == LiftConstants.leftRatchetUnlockPos &&
                rightRatchetServo.get() == LiftConstants.rightRatchetUnlockPos;
    }

    /**
     * Runs the lift motor forwards at a constant speed.
     */
    public void runUp() {
        if (this.ratchetsUnlocked()) {
            System.out.println("LEFT " + leftLiftEncoder.getPosition() + " RIGHT " + rightLiftEncoder.getPosition());
            if (leftLiftEncoder.getPosition() < LiftConstants.maxExtension) {
                leftLiftMotor.set(LiftConstants.motorSpeed);
            } else {
                leftLiftMotor.stopMotor();
            }
            if (rightLiftEncoder.getPosition() < LiftConstants.maxExtension) {
                rightLiftMotor.set(LiftConstants.motorSpeed);
            } else {
                rightLiftMotor.stopMotor();
            }
        } else {
            this.stop();
        }
    }

    /**
     * Runs the lift motor backwards at a constant speed.
     */
    public void runDown() {
        leftLiftMotor.set(LiftConstants.backwardsMotorSpeed);
        rightLiftMotor.set(LiftConstants.backwardsMotorSpeed);
    }

    /**
     * Stops both lift motors.
     *
     */
    public void stop() {
        leftLiftMotor.stopMotor();
        rightLiftMotor.stopMotor();
    }

    /**
     * Determines if both limit switches are pressed
     * 
     * @return are both limit switches pressed
     */
    public boolean bothLowerLimitsReached() {
        return leftLimitSwitch.isPressed() && rightLimitSwitch.isPressed();
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
