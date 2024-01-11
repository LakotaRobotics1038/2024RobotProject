package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lift extends SubsystemBase {
    private CANSparkMax leftLiftMotor = new CANSparkMax(0, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor = new CANSparkMax(0, MotorType.kBrushless);

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

    public void run (double speed) {
        leftLiftMotor.run(speed);
    }

    public void run (double speed) {
        rightLiftMotor.run(speed);
    }
}
