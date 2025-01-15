package frc.robot.subsystems;

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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ScoringElevatorConstants;

public class ScoringElevator extends SubsystemBase {

    private final SparkMax leftScoringElevatorMotor = new SparkMax(
            ScoringElevatorConstants.leftScoringElevatorMotorPort,
            MotorType.kBrushless);
    private final SparkMax rightScoringElevatorMotor = new SparkMax(
            ScoringElevatorConstants.rightScoringElevatorMotorPort,
            MotorType.kBrushless);

    private final SparkLimitSwitch leftScoringElevatorLimitSwitch = leftScoringElevatorMotor.getReverseLimitSwitch();
    private final SparkLimitSwitch rightScoringElevatorLimitSwitch = rightScoringElevatorMotor.getReverseLimitSwitch();

    private RelativeEncoder leftScoringElevatorEncoder = leftScoringElevatorMotor.getEncoder();
    private RelativeEncoder rightScoringElevatorEncoder = rightScoringElevatorMotor.getEncoder();

    private boolean enabled;
    private PIDController verticalController = new PIDController(
            ScoringElevatorConstants.kVerticalP,
            ScoringElevatorConstants.kVerticalI,
            ScoringElevatorConstants.kVerticalD);
    private PIDController errorController = new PIDController(
            ScoringElevatorConstants.kErrorP,
            ScoringElevatorConstants.kErrorI,
            ScoringElevatorConstants.kErrorD);

    public enum ElevatorSetpoints {
        Ground(ScoringElevatorConstants.groundSetpoint),
        Amp(ScoringElevatorConstants.ampSetpoint),
        Passer(ScoringElevatorConstants.passerSetpoint),
        Trap(ScoringElevatorConstants.trapSetpoint);

        public final double value;

        ElevatorSetpoints(double value) {
            this.value = value;
        }
    }

    private static ScoringElevator instance;

    public static ScoringElevator getInstance() {

        if (instance == null) {
            instance = new ScoringElevator();
        }
        return instance;
    }

    public ScoringElevator() {
        SparkMaxConfig leftScoringElevatorConfig = new SparkMaxConfig();
        SparkMaxConfig rightScoringElevatorConfig = new SparkMaxConfig();

        leftScoringElevatorConfig
                .idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        leftScoringElevatorConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);
        leftScoringElevatorConfig.encoder
                .positionConversionFactor(ScoringElevatorConstants.elevatorEncoderConversionFactor);

        rightScoringElevatorConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightScoringElevatorConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);
        rightScoringElevatorConfig.encoder
                .positionConversionFactor(ScoringElevatorConstants.elevatorEncoderConversionFactor);

        leftScoringElevatorMotor.configure(
                leftScoringElevatorConfig,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        rightScoringElevatorMotor.configure(
                rightScoringElevatorConfig,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        verticalController.disableContinuousInput();
        verticalController.setTolerance(0.5);

        errorController.disableContinuousInput();
        errorController.setTolerance(0.1);
        errorController.setSetpoint(0);
    }

    @Override
    public void periodic() {
        if (enabled) {
            double output = verticalController.calculate(getLeftPosition());
            double diff = getLeftPosition() - getRightPosition();
            double error = errorController.calculate(diff);

            double power = MathUtil.clamp(output, ScoringElevatorConstants.minSpeed, ScoringElevatorConstants.maxSpeed);
            double clampedError = MathUtil.clamp(error, ScoringElevatorConstants.minSpeed,
                    ScoringElevatorConstants.maxSpeed);

            double left = diff > 0 ? power + clampedError : power;
            double right = diff < 0 ? power - clampedError : power;
            leftScoringElevatorMotor.set(left);
            rightScoringElevatorMotor.set(right);
        }

        if (leftScoringElevatorLimitSwitch.isPressed() &&
                leftScoringElevatorEncoder.getPosition() != 0) {
            leftScoringElevatorEncoder.setPosition(0);
        }
        if (rightScoringElevatorLimitSwitch.isPressed() &&
                rightScoringElevatorEncoder.getPosition() != 0) {
            rightScoringElevatorEncoder.setPosition(0);
        }
    }

    /**
     * Returns the Left PIDController.
     *
     * @return The left controller.
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
    public void setSetpoint(ElevatorSetpoints setpoint) {
        verticalController.setSetpoint(setpoint.value);
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
     * Returns the position of the left scoring elevator encoder.
     *
     * @return double - current encoder position
     */
    public double getLeftPosition() {
        return leftScoringElevatorEncoder.getPosition();
    }

    /**
     * Returns the position of the right scoring elevator encoder.
     *
     * @return double - current encoder position
     */
    public double getRightPosition() {
        return rightScoringElevatorEncoder.getPosition();
    }

    private void stop() {
        leftScoringElevatorMotor.stopMotor();
        rightScoringElevatorMotor.stopMotor();
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
        this.stop();
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
