package frc.robot.subsystems;

import frc.robot.constants.VisionConstants;

import java.lang.Math;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RecieveVisionDataReturnDrivingAround extends SubsystemBase {

    private static Vision vision = Vision.getInstance();

    private static RecieveVisionDataReturnDrivingAround instance;

    public static RecieveVisionDataReturnDrivingAround getInstance() {
        if (instance == null) {
            instance = new RecieveVisionDataReturnDrivingAround();
        }
        return instance;
    }

    public RecieveVisionDataReturnDrivingAround() {
    }

    public double getAngle(int id) {
        double x = vision.getX(id);
        double y = vision.getY(id);
        if (x < VisionConstants.width / 2) {
            x = -vision.getX(id);
        }
        if (y < VisionConstants.height / 2) {
            y = -y;
        }
        return Math.toDegrees(Math.atan(x / y));
    }

    public double getDistance(int id) {
        double y = vision.getY(id);
        double oldY = 0.0;
        if (y > oldY) {
            oldY = y;
            return y;
        } else {
            oldY = 0.0;
            return -1;
        }
    }

}
