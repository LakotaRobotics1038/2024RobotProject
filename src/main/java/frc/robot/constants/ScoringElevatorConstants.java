package frc.robot.constants;

public class ScoringElevatorConstants {
    public static final int leftScoringElevatorMotorPort = 6;
    public static final int rightScoringElevatorMotorPort = 9;

    public static final double maxElevatorInches = 15.25;
    public static final double maxElevatorEncoderCounts = 22.0;
    public static final double elevatorEncoderConversionFactor = 1 / (maxElevatorEncoderCounts / maxElevatorInches);

    public static final double groundSetpoint = 0;
    public static final double ampSetpoint = 10.0;
    public static final double trapSetpoint = 14.0;

    public static final double maxDistance = 0;
    public static final double minDistance = maxElevatorInches;
    public static final double maxSpeed = 0.5;
    public static final double minSpeed = -0.25;

    public static final double kVerticalP = 0.8;
    public static final double kVerticalI = 0.0;
    public static final double kVerticalD = 0.0;
    public static final double kErrorP = 0.0;
    public static final double kErrorI = 0.0;
    public static final double kErrorD = 0.0;
}
