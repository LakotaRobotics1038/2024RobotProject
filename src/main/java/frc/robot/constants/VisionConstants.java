package frc.robot.constants;

public final class VisionConstants {
    public enum AprilTagHeights {
        SpeakerrHeighT(1.451102),
        AmpHeight(1.355852),
        StageHeight(1.322324);

        public final double height;

        AprilTagHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }

    public static final String kTableName = "Vision";
    public static final String kValuesTopic = "values";
    public static final String kRecordingTopic = "recording";
    public static final String kStreamCam0 = "shouldStream0";
    public static final String kEnabled0Topic = "on0";
    public static final String kEnabled1Topic = "on1";

    public static final double width = 640;
    public static final double height = 480;

    public static final double driveP = 0.005;
    public static final double driveI = 0;
    public static final double driveD = 0.0002;
    public static final double spinP = 0.005;
    public static final double spinI = 0;
    public static final double spinD = 0.0002;
    public static final double spinSetpoint = 0.0;
    public static final double aprilTagArea = 28908;

    public static final double cameraHorizontalFOV = 0;
    public static final double cameraVerticalFOV = 0;
    public static final double cameraResolution = 0;
    public static final double cameraHeight = 0;
    public static final double cameraAngleRad = 0 * (Math.PI / 180);

    public static final double noteHeight = 0;
}
