package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Map;

import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OperatorJoystick;
import frc.robot.autons.AutonSelector.AutonChoices;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private OperatorJoystick operatorJoystick = OperatorJoystick.getInstance();
    private Vision vision = Vision.getInstance();

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();

    // Tabs
    private final ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");
    private final ShuffleboardTab controlsTab = Shuffleboard.getTab("Controls");
    private final NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();

    // Variables
    private final Field2d field = new Field2d();
    private final HttpCamera camera;

    // Enums
    public enum Cameras {
        coneCamera,
        cubeCamera;
    }

    // Controls Tab Inputs
    private GenericEntry resetGyro = controlsTab.add("Reset Gyro", false)
            .withSize(1, 1)
            .withPosition(0, 0)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();
    // private GenericEntry shoulderP = driversTab.add("Shoulder P",
    // ShoulderConstants.kP)
    // .withPosition(2, 2)
    // .withSize(1, 1)
    // .getEntry();
    // private GenericEntry shoulderI = driversTab.add("Shoulder I",
    // ShoulderConstants.kI)
    // .withPosition(3, 2)
    // .withSize(1, 1)
    // .getEntry();
    // private GenericEntry shoulderD = driversTab.add("Shoulder D",
    // ShoulderConstants.kD)
    // .withPosition(4, 2)
    // .withSize(1, 1)
    // .getEntry();
    // private GenericEntry wristP = driversTab.add("Wrist P", WristConstants.kP)
    // .withPosition(2, 3)
    // .withSize(1, 1)
    // .getEntry();
    // private GenericEntry wristI = driversTab.add("Wrist I", WristConstants.kI)
    // .withPosition(3, 3)
    // .withSize(1, 1)
    // .getEntry();
    // private GenericEntry wristD = driversTab.add("Wrist D", WristConstants.kD)
    // .withPosition(4, 3)
    // .withSize(1, 1)
    // .getEntry();

    // Singleton Setup
    private static Dashboard instance;

    public static Dashboard getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Dashboard");
            instance = new Dashboard();
        }
        return instance;
    }

    private Dashboard() {
        super();
        camera = new HttpCamera("JetsonCamera", "http://10.10.38.15:1180/stream");
        camera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        tableInstance.getEntry("/CameraPublisher/JetsonCamera/streams").setStringArray(camera.getUrls());

        // TODO: This prevents you from switching tabs for some reason
        // Shuffleboard.selectTab("Drivers");

        driversTab.add("Auton Choices", autoChooser)
                .withPosition(0, 0)
                .withSize(2, 1);

        driversTab.addNumber("Gyro Heading", () -> {
            double angle = driveTrain.getHeading();
            angle %= 360;
            return angle < 0 ? angle + 360 : angle;
        })
                .withPosition(2, 0)
                .withSize(2, 1);
        // .withWidget(BuiltInWidgets.kGyro);

        driversTab.addNumber("Pose X", () -> {
            double poseX = driveTrain.getPose().getX();
            return poseX;
        })
                .withPosition(0, 1)
                .withSize(2, 1);

        driversTab.addNumber("Pose Y", () -> {
            double poseY = driveTrain.getPose().getY();
            return poseY;
        })
                .withPosition(0, 2)
                .withSize(2, 1);

        controlsTab.addNumber("Roll", driveTrain::getRoll)
                .withPosition(1, 0);

        driversTab.add(field)
                .withPosition(2, 1)
                .withSize(4, 3)
                .withWidget(BuiltInWidgets.kField);

        driversTab.add("Camera Stream", camera)
                .withPosition(6, 0)
                .withSize(4, 4);

        driversTab.addBoolean("Vision Enabled?", vision::isEnabled0)
                .withPosition(6, 0)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red"));

        controlsTab.add(field)
                .withPosition(2, 0)
                .withSize(8, 5)
                .withWidget(BuiltInWidgets.kField);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
        field.setRobotPose(driveTrain.getPose());
    }

    /**
     * Puts the given {@link PathPlannerTrajectory} on the dashboard
     *
     * @param trajectory
     */
    public void setTrajectory(PathPlannerTrajectory trajectory) {
        // this.field.getObject("traj").setTrajectory(trajectory);
    }

    /**
     * Puts the given {@link Trajectory} on the dashboard
     *
     * @param trajectory
     */
    public void setTrajectory(Trajectory trajectory) {
        this.field.getObject("traj").setTrajectory(trajectory);
    }

    /**
     * Removes the trajectory line from the dashboard
     */
    public void clearTrajectory() {
        this.field.getObject("traj").setPoses(new ArrayList<>());
    }

    /**
     * Gets the sendable chooser for Auton Modes
     *
     * @return
     */
    public SendableChooser<AutonChoices> getAutoChooser() {
        return autoChooser;
    }
}
