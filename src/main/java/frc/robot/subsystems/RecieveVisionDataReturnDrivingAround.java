package frc.robot.subsystems;

import frc.robot.constants.VisionConstants;

import java.lang.Math;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RecieveVisionDataReturnDrivingAround extends SubsystemBase {

    private static RecieveVisionDataReturnDrivingAround instance;

    public static RecieveVisionDataReturnDrivingAround getInstance() {
        if (instance == null) {
            instance = new RecieveVisionDataReturnDrivingAround();
        }
        return instance;
    }

    public RecieveVisionDataReturnDrivingAround() {
    }

    public double getAngle(double x, double y) {
        if (x < VisionConstants.width / 2) {
            x = -x;
        }
        if (y < VisionConstants.height / 2) {
            y = -y;
        }
        return Math.toDegrees(Math.atan(x / y));
    }

    /*
     * public double getDistance(double x, double y) {
     *
     * }
     */
}
