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
        double x1 = vision.getX(id);
        double x2 = vision.getX(id);
        double y1 = vision.getY(id);
        double y2 = vision.getY(id);
        double oldY = 0.0;

        if (id == 17) {
            if (y2 > oldY) {
                oldY = y2;
                return 0;
            } else {
                oldY = 0.0;
                return -1;
            }

        } else if (id >= 0 && id <= 15) {
            // code for april tags and area
            double length = Math.abs(x2 - x1);
            double width = Math.abs(y2 - y1);
            double area = (width * length);
            return area;
        } else {
            return 1;
        }
    }

}
