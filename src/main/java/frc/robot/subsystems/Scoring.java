package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;

import frc.robot.constants.ScoringConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import edu.wpi.first.math.MathUtil;

public class Scoring extends PIDSubsystem {

    public final CANSparkMax leftScoringElevatorMotor = new CANSparkMax(ScoringConstants.leftScoringElevatorMotorPort,
            MotorType.kBrushless);
    private final CANSparkMax rightScoringElevatorMotor = new CANSparkMax(
            ScoringConstants.rightScoringElevatorMotorPort, MotorType.kBrushless);
    private final CANSparkMax rollerMotor = new CANSparkMax(
            ScoringConstants.rollerMotorPort, MotorType.kBrushless);
    private final CANSparkMax leftLoadingMotor = new CANSparkMax(
            ScoringConstants.leftScoringElevatorMotorPort, MotorType.kBrushless);
    private final CANSparkMax rightLoadingMotor = new CANSparkMax(
            ScoringConstants.rightScoringElevatorMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder leftScoringElevatorEncoder = leftScoringElevatorMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public enum ElevatorSetpoints {
        ground(ScoringConstants.groundSetpoint),
        amp(ScoringConstants.ampSetpoint),
        trap(ScoringConstants.trapSetpoint);

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
        leftLoadingMotor.restoreFactoryDefaults();
        rightLoadingMotor.restoreFactoryDefaults();
        rollerMotor.setIdleMode(IdleMode.kCoast);
        rightScoringElevatorMotor.setIdleMode(IdleMode.kCoast);
        leftScoringElevatorMotor.setIdleMode(IdleMode.kCoast);
        leftLoadingMotor.setIdleMode(IdleMode.kCoast);
        rightLoadingMotor.setIdleMode(IdleMode.kCoast);
        rightScoringElevatorMotor.follow(leftScoringElevatorMotor);
        rightLoadingMotor.follow(leftLoadingMotor);
        rollerMotor.burnFlash();
        rightScoringElevatorMotor.burnFlash();
        leftScoringElevatorMotor.burnFlash();
        leftLoadingMotor.burnFlash();
        rightLoadingMotor.burnFlash();
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

    public void setClampSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, ScoringConstants.minSpeed, ScoringConstants.maxDistance);
        super.setSetpoint(setpoint);
    }

    public void setSetpoint(ElevatorSetpoints setpoint) {
        setSetpoint(setpoint.value);
    }

    public double getPosition() {
        return leftScoringElevatorEncoder.getPosition();
    }

    public void runLoader(double speed) {
        speed = MathUtil.clamp(speed, ScoringConstants.minSpeed, ScoringConstants.maxSpeed);
        leftLoadingMotor.set(speed);
    }

    public void runRoller(double speed) {
        speed = MathUtil.clamp(speed, ScoringConstants.minSpeed, ScoringConstants.maxSpeed);
        rollerMotor.set(speed);
    }

    public void stopRoller() {
        rollerMotor.stopMotor();
    }

    public void stopLoader() {
        leftLoadingMotor.stopMotor();
    }

    public void setP(double p) {
        getController().setP(p);
    }

    public void setI(double i) {
        getController().setI(i);
    }

    public void setD(double d) {
        getController().setD(d);
    }
}
