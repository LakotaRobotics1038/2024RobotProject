package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DrawbridgeConstants;

public class Drawbridge extends SubsystemBase {
    private Servo leftDrawbridgeServo = new Servo(DrawbridgeConstants.leftServoPort);
    private Servo rightDrawbridgeServo = new Servo(DrawbridgeConstants.rightServoPort);

    private static Drawbridge instance;

    public static Drawbridge getInstance() {
        if (instance == null) {
            instance = new Drawbridge();
        }
        return instance;
    }

    private Drawbridge() {
        leftDrawbridgeServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        rightDrawbridgeServo.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);
        this.up();
    }

    public void down() {
        leftDrawbridgeServo.set(DrawbridgeConstants.maxLeftDrawbridgeExtension);
        rightDrawbridgeServo.set(DrawbridgeConstants.maxRightDrawbridgeExtension);
    }

    public void up() {
        leftDrawbridgeServo.set(DrawbridgeConstants.minLeftDrawbridgeExtension);
        rightDrawbridgeServo.set(DrawbridgeConstants.minRightDrawbridgeExtension);
    }

    public double getPosition() {
        return leftDrawbridgeServo.get();
    }
}
