package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LiftConstants;

public final class Lift extends SubsystemBase {
    private static CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private static CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);

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
        rightLiftMotor.follow(leftLiftMotor);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    public static void runPos() {
        leftLiftMotor.set(LiftConstants.motorSpeed);
    }

    public static void runNeg() {
        leftLiftMotor.set(-LiftConstants.motorSpeed);
    }

    public static void stop() {
        leftLiftMotor.stopMotor();
    }
}
