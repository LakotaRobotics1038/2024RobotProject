package frc.robot.subsystems;

//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//import com.revrobotics.CANSparkMax.ControlType;
//import com.revrobotics.CANSparkMax.IdleMode;

public class Scoring extends SubsystemBase {

    private static Scoring instance;

    public static Scoring getInstance() {
        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }
}
