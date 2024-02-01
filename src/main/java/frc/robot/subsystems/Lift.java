package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LiftConstants;

public final class Lift extends SubsystemBase {
    private CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);

    private static Lift instance;

    public static Lift getInstance() {
        if (instance == null) {
            instance = new Lift();
        }
        return instance;
    }

    private Lift() {
        leftLiftMotor.restoreFactoryDefaults();
        rightLiftMotor.restoreFactoryDefaults();
        leftLiftMotor.setIdleMode(IdleMode.kCoast);
        rightLiftMotor.setIdleMode(IdleMode.kCoast);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    public void runLeftPos() {
        leftLiftMotor.set(LiftConstants.motorSpeed);
    }

    public void runRightPos() {
        rightLiftMotor.set(LiftConstants.motorSpeed);
    }

    public void runLeftNeg() {
        leftLiftMotor.set(-LiftConstants.motorSpeed);
    }

    public void runRightNeg() {
        rightLiftMotor.set(-LiftConstants.motorSpeed);
    }

    public void stopLeftMotor() {
        leftLiftMotor.stopMotor();
    }

    public void stopRightMotor() {
        rightLiftMotor.stopMotor();
    }
}
