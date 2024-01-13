package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.MotorType;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lift extends SubsystemBase {
    private CANSparkMax leftLiftMotor = new CANSparkMax(LiftConstants.leftLiftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(LiftConstants.rightLiftMotorPort, MotorType.kBrushless);

    private static Lift instance;

    public void getInstance() {
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

    public void run () {
        leftLiftMotor.set(LiftConstants.motorSpeed);
    }

    public void stop () {
        leftLiftMotor.stopMotor();
    }
}
