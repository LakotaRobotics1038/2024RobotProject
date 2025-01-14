package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DrawbridgeConstants;

public class Drawbridge extends SubsystemBase {
    private Servo leftServo = new Servo(DrawbridgeConstants.leftServoPort);
    private Servo rightServo = new Servo(DrawbridgeConstants.rightServoPort);

    private static Drawbridge instance;

    public static Drawbridge getInstance() {
        if (instance == null) {
            instance = new Drawbridge();
        }
        return instance;
    }

    private Drawbridge() {
        leftServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        this.up();
    }

    public void down() {
        leftServo.set(DrawbridgeConstants.maxLeftExtension);
        rightServo.set(DrawbridgeConstants.maxRightExtension);
    }

    public void up() {
        leftServo.set(DrawbridgeConstants.minLeftExtension);
        rightServo.set(DrawbridgeConstants.minRightExtension);
    }

    public double getPosition() {
        return leftServo.get();
    }
}
