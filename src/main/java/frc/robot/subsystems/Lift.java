package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LiftConstants;
import frc.robot.constants.NeoMotorConstants;

public final class Lift extends SubsystemBase {
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);
    private RelativeEncoder leftLiftEncoder = leftLiftMotor.getEncoder();
    private RelativeEncoder rightLiftEncoder = rightLiftMotor.getEncoder();
    private SparkLimitSwitch leftLimitSwitch = leftLiftMotor.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
    private SparkLimitSwitch rightLimitSwitch = rightLiftMotor
            .getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    private Servo leftRatchetServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightRatchetServo = new Servo(LiftConstants.rightServoPort);

    private PIDController verticalController = new PIDController(
            LiftConstants.kVerticalP,
            LiftConstants.kVerticalI,
            LiftConstants.kVerticalD);
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

        verticalController.disableContinuousInput();
        verticalController.setTolerance(LiftConstants.tolerance);

        errorController.disableContinuousInput();
        errorController.setTolerance(LiftConstants.tolerance);
        errorController.setSetpoint(0);
    }

    /**
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
    private void runLeft(double speed) {
        speed = MathUtil.clamp(speed, LiftConstants.downSpeed, LiftConstants.upSpeed);
        if (speed > 0 && this.leftRatchetUnlocked()) {
            if (!isLiftUp()) {
                leftLiftMotor.set(speed);
            } else {
                leftLiftMotor.stopMotor();
            }
        } else if (speed < 0) {
            leftLiftMotor.set(speed);
        } else {
            leftLiftMotor.stopMotor();
        }
    }

    /**
     * Runs the right lift motor up at a constant speed.
     */
    private void runRight(double speed) {
        speed = MathUtil.clamp(speed, LiftConstants.downSpeed, LiftConstants.upSpeed);
        if (speed > 0 && this.rightRatchetUnlocked()) {
            if ((!isLiftUp())) {
                rightLiftMotor.set(speed);
            } else {
                rightLiftMotor.stopMotor();
            }
        } else if (speed < 0) {
            rightLiftMotor.set(speed);
        } else {
            rightLiftMotor.stopMotor();
        }
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
            double output = verticalController.calculate(getLeftPosition());
            double pitch = driveTrain.getPitch();
            double error = errorController.calculate(pitch);

            double power = MathUtil.clamp(output, LiftConstants.downSpeed, LiftConstants.upSpeed);
            double clampedError = MathUtil.clamp(error, LiftConstants.downSpeed, LiftConstants.upSpeed);

            double left = pitch > 0 ? power + clampedError : power;
            double right = pitch < 0 ? power - clampedError : power;
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
     * Returns the vertical PIDController.
     *
     * @return The vertical controller.
     */
    public PIDController getVerticalController() {
        return verticalController;
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
     * Sets the Scoring PID setpoint to one of the constant setpoints.
     *
     * @param ElevatorSetpoints - desired setpoint
     */
    public void setSetpoint(double setpoint) {
        verticalController.setSetpoint(MathUtil.clamp(setpoint, 0, LiftConstants.maxExtension));
    }

    /**
     * Returns the current setpoint of the subsystem.
     *
     * @return The current setpoint
     */
    public double getSetpoint() {
        return verticalController.getSetpoint();
    }

    /**
     * Returns the position of the left lift encoder.
     *
     * @return double - current encoder position
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

    public boolean isLiftUp() {
        return rightLiftEncoder.getPosition() < LiftConstants.maxExtension;
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
     * Stops left lift motor.
     */
    public void stopMotors() {
        leftLiftMotor.stopMotor();
        rightLiftMotor.stopMotor();
    }

    /** Enables the PID control. Resets the controller. */
    public void enable() {
        enabled = true;
        verticalController.reset();
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

    public boolean onTarget() {
        return getVerticalController().atSetpoint();
    }
}