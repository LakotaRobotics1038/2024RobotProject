package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.constants.LiftConstants;

public final class Lift extends SubsystemBase {
    private CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightMotorPort, MotorType.kBrushless);

    private Servo leftServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightServo = new Servo(LiftConstants.rightServoPort);

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

        leftServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    public void enableRatchets() {
        leftServo.set(1);
        rightServo.set(1);
    }

    public void disableRatchets() {
        leftServo.set(0);
        rightServo.set(0);
    }

    public void runPos() {
        leftLiftMotor.set(LiftConstants.motorSpeed);
    }

    public void runNeg() {
        leftLiftMotor.set(-LiftConstants.motorSpeed);
    }

    public void stop() {
        leftLiftMotor.stopMotor();
    }
}