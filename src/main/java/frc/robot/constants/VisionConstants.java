package frc.robot.constants;

public final class VisionConstants {
    public enum AprilTagHeights {
        SpeakerHeight(1.451102),
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
    public static final String kEnabledTopic = "enable";

    public static final double width = 320;
    public static final double height = 180;

    public static final double driveP = 0.005;
    public static final double driveI = 0;
    public static final double driveD = 0.0002;
    public static final double spinP = 0.005;
    public static final double spinI = 0;
    public static final double spinD = 0.0002;
    public static final double spinSetpoint = 0.0;
    public static final double aprilTagArea = 28908;
    // Meters
    public static final double aprilTagLengthAndWidth = 0.1651;

    public static final double cameraHorizontalFOV = 100;
    public static final double cameraVerticalFOV = 100;
    public static final double cameraResolutionX = 680;
    public static final double cameraResolutionY = 480;
    public static final double cameraHeight = 0;
    public static final double cameraAngleRad = 0 * (Math.PI / 180);

    public static final double noteHeight = 0;

}
