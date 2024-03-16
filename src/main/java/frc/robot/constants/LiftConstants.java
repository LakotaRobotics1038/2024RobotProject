package frc.robot.constants;

public class LiftConstants {
    public static final int leftMotorPort = 14;
    public static final int rightMotorPort = 5;

    public static final int leftServoPort = 0;
    public static final int rightServoPort = 1;

    public static final double leftRatchetLockPos = 0.7;
    public static final double leftRatchetUnlockPos = 0.9;
    public static final double rightRatchetLockPos = 0.55;
    public static final double rightRatchetUnlockPos = 0.65;

    public static final double upSpeed = 0.55;
    public static final double downSpeed = -0.9;

    public static final double maxExtension = 195;
    public static final double maxLiftInches = 27;
    public static final double minLiftInches = 0;

    public static final double maxPower = 1.0;

    public static final double kVerticalP = 0.01;
    public static final double kVerticalI = 0.0;
    public static final double kVerticalD = 0.0;

    public static final double kErrorP = 0.01;
    public static final double kErrorI = 0.0;
    public static final double kErrorD = 0.0;

    public static final double tolerance = 1.0;
    public static final double encoderConversion = 1 / (maxExtension / maxLiftInches);
}