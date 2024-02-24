package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkFlex;

import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ScoringConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import edu.wpi.first.math.MathUtil;

public class Scoring extends PIDSubsystem {

    private final CANSparkMax leftScoringElevatorMotor = new CANSparkMax(ScoringConstants.leftScoringElevatorMotorPort,
            MotorType.kBrushless);
    private final CANSparkMax rightScoringElevatorMotor = new CANSparkMax(
            ScoringConstants.rightScoringElevatorMotorPort, MotorType.kBrushless);
    private final CANSparkFlex rollerMotor = new CANSparkFlex(
            ScoringConstants.rollerMotorPort, MotorType.kBrushless);

    private AbsoluteEncoder leftScoringElevatorEncoder = leftScoringElevatorMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public enum ElevatorSetpoints {
        Ground(ScoringConstants.groundSetpoint),
        Amp(ScoringConstants.ampSetpoint),
        Trap(ScoringConstants.trapSetpoint);

        public final double value;

        ElevatorSetpoints(double value) {
            this.value = value;
        }
    }

    private static Scoring instance;

    public static Scoring getInstance() {

        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }

    public Scoring() {
        super(new PIDController(ScoringConstants.kP, ScoringConstants.kI, ScoringConstants.kD));

        rollerMotor.restoreFactoryDefaults();
        rightScoringElevatorMotor.restoreFactoryDefaults();
        leftScoringElevatorMotor.restoreFactoryDefaults();

        rollerMotor.setIdleMode(IdleMode.kCoast);
        rightScoringElevatorMotor.setIdleMode(IdleMode.kCoast);
        leftScoringElevatorMotor.setIdleMode(IdleMode.kCoast);

        rightScoringElevatorMotor.follow(leftScoringElevatorMotor);

        leftScoringElevatorMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightScoringElevatorMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rollerMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);

        rollerMotor.burnFlash();
        rightScoringElevatorMotor.burnFlash();
        leftScoringElevatorMotor.burnFlash();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ScoringConstants.maxSpeed, ScoringConstants.maxSpeed);
        leftScoringElevatorMotor.set(power);
    }

    @Override
    protected double getMeasurement() {
        return getPosition();
    }

    /**
     * Sets the Scoring PID setpoint to a clamped value.
     *
     * @param double - desired setpoint
     */
    public void setClampSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, ScoringConstants.minSpeed, ScoringConstants.maxDistance);
        super.setSetpoint(setpoint);
    }

    /**
     * Sets the Scoring PID setpoint to one of the constant setpoints.
     *
     * @param ElevatorSetpoints - desired setpoint
     */
    public void setSetpoint(ElevatorSetpoints setpoint) {
        setSetpoint(setpoint.value);
    }

    /**
     * Returns the position of the scoring elevator encoder.
     *
     * @return double - current encoder position
     */
    public double getPosition() {
        return leftScoringElevatorEncoder.getPosition();
    }

    /**
     * Runs the scoring roller at the constant speed designated for regular scoring.
     */
    public void runRoller() {
        rollerMotor.set(ScoringConstants.rollerSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for shooting.
     */
    public void rollerShoot() {
        rollerMotor.set(-ScoringConstants.rollerSpeed);
    }

    /**
     * Stops the scoring roller.
     */
    public void stopRoller() {
        rollerMotor.stopMotor();
    }

    /**
     * Sets P value for scoring PID.
     *
     * @param double - desired P value
     */
    public void setP(double p) {
        getController().setP(p);
    }

    /**
     * Sets I value for scoring PID.
     *
     * @param double - desired I value
     */
    public void setI(double i) {
        getController().setI(i);
    }

    /**
     * Sets D value for scoring PID.
     *
     * @param double - desired D value
     */
    public void setD(double d) {
        getController().setD(d);
    }
}
