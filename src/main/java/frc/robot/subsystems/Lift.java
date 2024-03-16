package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.constants.LiftConstants;
import frc.robot.constants.NeoMotorConstants;

public final class Lift extends PIDSubsystem {
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
        super(new PIDController(LiftConstants.kP, LiftConstants.kI, LiftConstants.kD));
        getController().setTolerance(LiftConstants.tolerance);
        getController().enableContinuousInput(0, LiftConstants.encoderConversion);
        leftLiftMotor.restoreFactoryDefaults();
        rightLiftMotor.restoreFactoryDefaults();

        leftLiftMotor.setIdleMode(IdleMode.kBrake);
        rightLiftMotor.setIdleMode(IdleMode.kBrake);

        leftLimitSwitch.enableLimitSwitch(true);
        rightLimitSwitch.enableLimitSwitch(true);

        rightLiftMotor.follow(leftLiftMotor);

        leftLiftMotor.setInverted(false);
        rightLiftMotor.setInverted(true);

        leftRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        leftLiftMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightLiftMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    public void enableRatchets() {
        rightRatchetServo.set(LiftConstants.rightRatchetLockPos);
        leftRatchetServo.set(LiftConstants.leftRatchetLockPos);
    }

    public void disableRatchets() {
        leftRatchetServo.set(LiftConstants.leftRatchetUnlockPos);
        rightRatchetServo.set(LiftConstants.rightRatchetUnlockPos);

    }

    public boolean ratchetsUnlocked() {
        return rightRatchetServo.get() == LiftConstants.rightRatchetUnlockPos
                && leftRatchetServo.get() == LiftConstants.leftRatchetUnlockPos;
    }

    public double getPosition() {
        return leftLiftEncoder.getPosition();
    }

    public void runLiftUp() {
        if (this.ratchetsUnlocked()) {
            if ((isLiftUp())) {
                leftLiftMotor.set(LiftConstants.motorSpeed);
            } else {
                stopMotors();
            }
        } else {
            stopMotors();
        }
    }

    public boolean isLiftUp() {
        return rightLiftEncoder.getPosition() < LiftConstants.maxExtension;
    }

    public boolean onTarget() {
        return getController().atSetpoint();
    }

    public void runLiftDown() {
        leftLiftMotor.set(LiftConstants.backwardsMotorSpeed);
    }

    /**
     * Stops left lift motor.
     */
    public void stopMotors() {
        leftLiftMotor.stopMotor();
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

    @Override
    protected double getMeasurement() {
        return getPosition();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -LiftConstants.maxPower, LiftConstants.maxPower);
        leftLiftMotor.set(power);
    }
}
