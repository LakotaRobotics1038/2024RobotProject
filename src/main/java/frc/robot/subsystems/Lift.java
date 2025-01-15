package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LiftConstants;
import frc.robot.constants.NeoMotorConstants;

public final class Lift extends SubsystemBase {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private SparkMax leftLiftMotor = new SparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private SparkMax rightLiftMotor = new SparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);
    private RelativeEncoder leftLiftEncoder = leftLiftMotor.getEncoder();
    private RelativeEncoder rightLiftEncoder = rightLiftMotor.getEncoder();
    private SparkLimitSwitch leftLimitSwitch = leftLiftMotor.getReverseLimitSwitch();
    private SparkLimitSwitch rightLimitSwitch = rightLiftMotor.getReverseLimitSwitch();

    private Servo leftRatchetServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightRatchetServo = new Servo(LiftConstants.rightServoPort);

    private PIDController verticalControllerLeft = new PIDController(
            LiftConstants.kVerticalLeftUpP,
            LiftConstants.kVerticalLeftUpI,
            LiftConstants.kVerticalLeftUpD);
    private PIDController verticalControllerRight = new PIDController(
            LiftConstants.kVerticalRightUpP,
            LiftConstants.kVerticalRightUpI,
            LiftConstants.kVerticalRightUpD);
    private PIDController errorController = new PIDController(
            LiftConstants.kErrorP,
            LiftConstants.kErrorI,
            LiftConstants.kErrorD);

    private boolean enabled;

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
        SparkMaxConfig leftLiftConfig = new SparkMaxConfig();
        SparkMaxConfig rightLiftConfig = new SparkMaxConfig();

        leftLiftConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        leftLiftConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);
        leftLiftConfig.encoder
                .positionConversionFactor(LiftConstants.encoderConversion);

        rightLiftConfig
                .idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightLiftConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);
        rightLiftConfig.encoder
                .positionConversionFactor(LiftConstants.encoderConversion);

        leftLiftMotor.configure(leftLiftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightLiftMotor.configure(rightLiftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        leftRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        verticalControllerLeft.disableContinuousInput();
        verticalControllerLeft.setTolerance(LiftConstants.tolerance);
        verticalControllerRight.disableContinuousInput();
        verticalControllerRight.setTolerance(LiftConstants.tolerance);

        errorController.disableContinuousInput();
        errorController.setSetpoint(0);
    }

    /**
     * Enables the lift ratchets (sets them to a constant maximum extension).
     */
    public void lockRatchets() {
        leftRatchetServo.set(LiftConstants.leftRatchetLockPos);
        rightRatchetServo.set(LiftConstants.rightRatchetLockPos);
    }

    /**
     * Disables the lift ratchets (sets them to a constant minimum extension).
     */
    public void unlockRatchets() {
        leftRatchetServo.set(LiftConstants.leftRatchetUnlockPos);
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
     * Runs the passed motor at a passed speed until the passed encoder reaches max
     * extension, but only if the ratchet is unlocked
     *
     * @param motor           The motor to set the speed of
     * @param encoder         The encoder to measure the position of the passed
     *                        motor
     * @param ratchetUnlocked A boolean supplier to determine if the ratchet for
     *                        this motor is unlocked
     * @param speed           The speed at which to move the motor
     */
    private void run(SparkMax motor, RelativeEncoder encoder, BooleanSupplier ratchetUnlocked, double speed) {
        speed = MathUtil.clamp(speed, LiftConstants.downSpeed, LiftConstants.upSpeed);
        if (speed > 0 && ratchetUnlocked.getAsBoolean()) {
            if (encoder.getPosition() < LiftConstants.maxExtension) {
                motor.set(LiftConstants.maxExtension);
            } else {
                motor.stopMotor();
            }
        } else if (speed < 0) {
            motor.set(speed);
        } else {
            motor.stopMotor();
        }
    }

    /**
     * Runs the left lift motor up at a constant speed.
     */
    private void runLeft(double speed) {
        this.run(leftLiftMotor, leftLiftEncoder, this::leftRatchetUnlocked, speed);
    }

    /**
     * Runs the right lift motor up at a constant speed.
     */
    private void runRight(double speed) {
        this.run(rightLiftMotor, rightLiftEncoder, this::rightRatchetUnlocked, speed);
    }

    /**
     * Runs the left lift motor down at a constant speed.
     */
    public void runLeftDown() {
        leftLiftMotor.set(LiftConstants.downSpeed);
    }

    /**
     * Runs the right lift motor down at a constant speed.
     */
    public void runRightDown() {
        rightLiftMotor.set(LiftConstants.downSpeed);
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
        if (enabled) {
            double leftOutput = verticalControllerLeft.calculate(getLeftPosition());
            double rightOutput = verticalControllerRight.calculate(getRightPosition());
            double roll = driveTrain.getRoll();
            double error = errorController.calculate(roll);

            double leftPower = MathUtil.clamp(leftOutput, LiftConstants.downSpeed, LiftConstants.upSpeed);
            double rightPower = MathUtil.clamp(rightOutput, LiftConstants.downSpeed, LiftConstants.upSpeed);
            double clampedError = MathUtil.clamp(error, LiftConstants.downSpeed, LiftConstants.upSpeed);

            double left = roll < 0 ? leftPower + clampedError : leftPower;
            double right = roll > 0 ? rightPower - clampedError : rightPower;

            runLeft(left);
            runRight(right);
        }

        if (leftLimitSwitch.isPressed() && leftLiftEncoder.getPosition() != 0) {
            leftLiftEncoder.setPosition(0);
        }

        if (rightLimitSwitch.isPressed() && rightLiftEncoder.getPosition() != 0) {
            rightLiftEncoder.setPosition(0);
        }
    }

    /**
     * Returns the Left vertical PIDController.
     *
     * @return The Left vertical controller.
     */
    public PIDController getLeftVerticalController() {
        return verticalControllerLeft;
    }

    /**
     * Returns the RIght vertical PIDController.
     *
     * @return The Right vertical controller.
     */
    public PIDController getRightVerticalController() {
        return verticalControllerRight;
    }

    /**
     * Returns the Right PIDController.
     *
     * @return The right controller.
     */
    public PIDController getErrorController() {
        return errorController;
    }

    /**
     * Sets the Left Lift PID setpoint to one of the constant setpoints.
     *
     * @param Double - desired setpoint
     */
    public void setSetpointLeft(double setpoint) {
        verticalControllerLeft
                .setSetpoint(MathUtil.clamp(setpoint, LiftConstants.minExtension, LiftConstants.maxExtension));
    }

    /**
     * Sets the Right Lift PID setpoint to one of the constant setpoints.
     *
     * @param Double - desired setpoint
     */
    public void setSetpointRight(double setpoint) {
        verticalControllerRight
                .setSetpoint(MathUtil.clamp(setpoint, LiftConstants.minExtension, LiftConstants.maxExtension));
    }

    /**
     * Returns the current setpoint of the subsystem.
     *
     * @return The current setpoint of the Left Lift
     */
    public double getLeftSetpoint() {
        return verticalControllerLeft.getSetpoint();
    }

    /**
     * Returns the current setpoint of the subsystem.
     *
     * @return The current setpoint of the Right Lift
     */
    public double getRightSetpoint() {
        return verticalControllerRight.getSetpoint();
    }

    /**
     * Returns the position of the left lift encoder.
     *
     * @return current encoder position
     */
    public double getLeftPosition() {
        return leftLiftEncoder.getPosition();
    }

    /**
     * Returns the position of the right lift encoder.
     *
     * @return double - current encoder position
     */
    public double getRightPosition() {
        return rightLiftEncoder.getPosition();
    }

    /**
     * Returns whether or not lift is up
     *
     * @return whether lift is up or not
     */
    public boolean isLiftUp() {
        return rightLiftEncoder.getPosition() >= LiftConstants.maxExtension
                && leftLiftEncoder.getPosition() >= LiftConstants.maxExtension;
    }

    /**
     * Stops left lift motor.
     */
    public void stopMotors() {
        leftLiftMotor.stopMotor();
        rightLiftMotor.stopMotor();
    }

    /** Enables the PID control. Resets the controller. */
    public void enable() {
        enabled = true;
        verticalControllerLeft.reset();
        verticalControllerRight.reset();
        errorController.reset();
    }

    /** Disables the PID control. Sets output to zero. */
    public void disable() {
        enabled = false;
        this.stopMotors();
    }

    /**
     * Returns whether the controller is enabled.
     *
     * @return Whether the controller is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }
}