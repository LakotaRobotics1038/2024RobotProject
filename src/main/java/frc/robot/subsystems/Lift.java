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

    private Servo leftRatchetServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightRatchetServo = new Servo(LiftConstants.rightServoPort);

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

        leftRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        leftLiftMotor.burnFlash();
        rightLiftMotor.burnFlash();
    }

    /**
     * Enables the lift ratchets (sets them to a contant maximum extension).
     */
    public void enableRatchets() {
        leftRatchetServo.set(LiftConstants.ratchetMaxExtend);
        rightRatchetServo.set(LiftConstants.ratchetMaxExtend);
    }

    /**
     * Disables the lift ratchets (sets them to a constant minimum extension).
     */
    public void disableRatchets() {
        leftRatchetServo.set(LiftConstants.ratchetMinExtend);
        rightRatchetServo.set(LiftConstants.ratchetMinExtend);
    }

    /**
     * Runs the lift motor forwards at a constant speed.
     */
    public void runPos() {
        leftLiftMotor.set(LiftConstants.motorSpeed);
    }
    
    /**
     * Runs the lift motor backwards at a constant speed.
     */
    public void runNeg() {
        leftLiftMotor.set(LiftConstants.backwardsMotorSpeed);
    }
    
    /**
     * Stops both lift motors.
     *
     */
    public void stop() {
        leftLiftMotor.stopMotor();
    }
}
