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

    private final CANSparkMax leftScoringElevatorMotor = new CANSparkMax(ScoringConstants.kLeftScoringElevatorMotorPort,
            MotorType.kBrushless);
    private final CANSparkMax rightScoringElevatorMotor = new CANSparkMax(
            ScoringConstants.kRightScoringElevatorMotorPort, MotorType.kBrushless);
    private final CANSparkMax rollerMotor = new CANSparkMax(
            ScoringConstants.kRollerMotorPort, MotorType.kBrushless);

    private AbsoluteEncoder leftScoringElevatorEncoder = leftScoringElevatorMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public enum ElevatorSetpoints {
        ground(ScoringConstants.kGroundSetpoint),
        amp(ScoringConstants.kAmpSetpoint),
        trap(ScoringConstants.kTrapSetpoint);

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
        rollerMotor.burnFlash();
        rightScoringElevatorMotor.burnFlash();
        leftScoringElevatorMotor.burnFlash();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ScoringConstants.kMaxSpeed, ScoringConstants.kMaxSpeed);
        leftScoringElevatorMotor.set(power);
    }

    @Override
    protected double getMeasurement() {
        return getPosition();
    }

    public void setClampSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, ScoringConstants.kMinSpeed, ScoringConstants.kMaxDistance);
        super.setSetpoint(setpoint);
    }

    public void setSetpoint(ElevatorSetpoints setpoint) {
        setSetpoint(setpoint.value);
    }

    public double getPosition() {
        return leftScoringElevatorEncoder.getPosition();
    }

    public void runRoller(double speed) {
        speed = MathUtil.clamp(speed, ScoringConstants.kMinSpeed, ScoringConstants.kMaxSpeed);
        rollerMotor.set(speed);
    }

    public void stopRoller() {
        rollerMotor.stopMotor();
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
