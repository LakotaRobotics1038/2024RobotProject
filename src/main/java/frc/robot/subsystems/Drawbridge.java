package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DrawbridgeConstants;
import frc.robot.constants.LiftConstants;

public class Drawbridge extends SubsystemBase {
    private Servo leftRatchetServo = new Servo(LiftConstants.leftServoPort);
    private Servo rightRatchetServo = new Servo(LiftConstants.rightServoPort);

    private static Drawbridge instance;

    public static Drawbridge getInstance() {
        if (instance == null) {
            instance = new Drawbridge();
        }
        return instance;
    }

    private Drawbridge() {
        leftRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightRatchetServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
    }

    public void DrawbridgeDown() {
        leftRatchetServo.set(DrawbridgeConstants.maxDrawbridgeExtension);
        rightRatchetServo.set(DrawbridgeConstants.maxDrawbridgeExtension);
    }

    public void DrawbridgeUp() {
        leftRatchetServo.set(DrawbridgeConstants.minDrawbridgeExtension);
        rightRatchetServo.set(DrawbridgeConstants.minDrawbridgeExtension);
    }

    public double getDrawbridgePos() {
        return leftRatchetServo.get();
    }
}
