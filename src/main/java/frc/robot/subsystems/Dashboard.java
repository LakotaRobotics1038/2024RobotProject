package frc.robot.subsystems;

import java.util.ArrayList;

import com.pathplanner.lib.path.PathPlannerTrajectory;
import com.pathplanner.lib.util.PathPlannerLogging;

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
import frc.robot.autons.AutonSelector.AutonChoices;
import frc.robot.constants.ScoringConstants;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Scoring scoring = Scoring.getInstance();
    private Vision vision = Vision.getInstance();

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();
    private SendableChooser<Double> delayChooser = new SendableChooser<>();

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

    // Drivers Tab Inputs
    private GenericEntry feedAmpSpeed = driversTab.add("Feed Amp Speed", ScoringConstants.feedAmpSpeed)
            .withPosition(0, 2)
            .getEntry();

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

        driversTab.add("Delay Choices", delayChooser)
                .withPosition(0, 1)
                .withSize(2, 1);

        driversTab.addNumber("Gyro", () -> {
            double angle = driveTrain.getHeading();
            angle %= 360;
            return angle < 0 ? angle + 360 : angle;
        })
                .withPosition(2, 0)
                .withSize(2, 1);
        // .withWidget(BuiltInWidgets.kGyro);

        controlsTab.addNumber("Roll", driveTrain::getRoll)
                .withPosition(1, 0);

        driversTab.add(field)
                .withPosition(2, 1)
                .withSize(4, 3)
                .withWidget(BuiltInWidgets.kField);

        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            field.getObject("target pose").setPose(pose);
        });

        PathPlannerLogging.setLogActivePathCallback((poses) -> {
            field.getObject("poses").setPoses(poses);
        });

        driversTab.add("Camera Stream", camera)
                .withPosition(6, 0)
                .withSize(4, 4);

        // driversTab.addBoolean("Vision Enabled?", vision::isEnabled0)
        // .withPosition(6, 0)
        // .withWidget(BuiltInWidgets.kBooleanBox)
        // .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "red"));

        controlsTab.add(field)
                .withPosition(2, 0)
                .withSize(8, 5)
                .withWidget(BuiltInWidgets.kField);

        // driversTab.addDouble("LeftScoreLiftEnc", scoring::getLeftPosition)
        // .withPosition(0, 3);
        // driversTab.addDouble("RightScoreLiftEnc", scoring::getRightPosition)
        // .withPosition(1, 3);

        // driversTab.add("Vert Controller",
        // scoring.getVerticalController())
        // .withPosition(0, 1)
        // .withWidget(BuiltInWidgets.kPIDController);

        // driversTab.add("Err Controller",
        // scoring.getErrorController())
        // .withPosition(1, 1)
        // .withWidget(BuiltInWidgets.kPIDController);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
        field.setRobotPose(driveTrain.getPose());
        scoring.setFeedAmpSpeed(feedAmpSpeed.getDouble(ScoringConstants.feedAmpSpeed));
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

    public SendableChooser<Double> getDelayChooser() {
        return delayChooser;
    }
}
