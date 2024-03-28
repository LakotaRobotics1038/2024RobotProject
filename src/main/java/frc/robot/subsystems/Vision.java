package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;
import frc.robot.utils.VisionData;

public class Vision extends SubsystemBase {
    // Enum for different things vision can find
    public enum VisionTarget {
        APR1(0),
        APT2(1),
        APT3(2),
        APT4(3),
        APT5(4),
        APT6(5),
        APT7(6),
        APT8(7),
        APT9(8),
        APT10(9),
        APT11(10),
        APT12(11),
        APT13(12),
        APT14(13),
        APT15(14),
        APT16(15),
        GAVIN(16),
        NOTE(17);

        public final int id;

        VisionTarget(int id) {
            this.id = id;
        }
    }

    public enum CameraStream {
        cam0,
        cam1;
    }

    // Instance Values
    private JSONParser jsonParser = new JSONParser();
    private boolean recording = false;
    private boolean enabled0 = false;
    private boolean enabled1 = false;
    private List<VisionData> visionData;

    // Network Tables Setup
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable(VisionConstants.kTableName);
    BooleanTopic recordingTopic = table.getBooleanTopic(VisionConstants.kRecordingTopic);
    BooleanPublisher recordingPublisher = recordingTopic.publish();
    BooleanTopic enabled0Topic = table.getBooleanTopic(VisionConstants.kEnabled0Topic);
    BooleanPublisher enabled0Publisher = enabled0Topic.publish();
    BooleanTopic enabled1Topic = table.getBooleanTopic(VisionConstants.kEnabled1Topic);
    BooleanPublisher enabled1Publisher = enabled1Topic.publish();
    BooleanTopic streamCam0Topic = table.getBooleanTopic(VisionConstants.kStreamCam0);
    BooleanPublisher streamCam0Publisher = streamCam0Topic.publish();
    StringTopic valuesTopic = table.getStringTopic(VisionConstants.kValuesTopic);
    StringSubscriber valuesSubscriber = valuesTopic.subscribe("[]");

    // Singleton Setup
    private static Vision instance;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private Vision() {
    }

    @Override
    public void periodic() {
        String value = valuesSubscriber.get();

        try {
            JSONArray unparsedData = (JSONArray) jsonParser.parse(value);
            visionData = new ArrayList<VisionData>();
            for (Object data : unparsedData) {
                JSONObject jsonObject = (JSONObject) data;
                VisionData mappedData = new VisionData((String) jsonObject.get("center x"),
                        (String) jsonObject.get("center y"), (String) jsonObject.get("top left x"),
                        (String) jsonObject.get("top left y"), (String) jsonObject.get("top right x"),
                        (String) jsonObject.get("top right y"), (String) jsonObject.get("bottom left x"),
                        (String) jsonObject.get("bottom left y"), (String) jsonObject.get("bottom right x"),
                        (String) jsonObject.get("bottom right y"),
                        (String) jsonObject.get("conf"),
                        (String) jsonObject.get("target"));
                visionData.add(mappedData);
            }
        } catch (ParseException ex) {
            System.out.println("Failed to parse vision data");
        } catch (NumberFormatException ex) {
            System.out.println("Bad number in vision data");
        }
    }

    public void setCamStream(CameraStream cam) {
        streamCam0Publisher.set(cam == CameraStream.cam0);
    }

    public void startRecording() {
        recording = true;
        recordingPublisher.set(recording);
    }

    public void stopRecording() {
        recording = false;
        recordingPublisher.set(recording);
    }

    public void enable0() {
        enabled0 = true;
        enabled0Publisher.set(enabled0);
    }

    public void disable0() {
        enabled0 = false;
        enabled0Publisher.set(enabled0);
    }

    public boolean isEnabled0() {
        return enabled0;
    }

    public void enable1() {
        enabled1 = true;
        enabled1Publisher.set(enabled1);
    }

    public void disable1() {
        enabled1 = false;
        enabled1Publisher.set(enabled1);
    }

    public boolean isEnabled1() {
        return enabled1;
    }

    public double getCenterX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getCenterX();
            }
        }
        return -1;
    }

    public double getCenterY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getCenterY();
            }
        }
        return -1;
    }

    public double getTopLeftX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getTopLeftX();
            }
        }
        return -1;
    }

    public double getTopLeftY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getTopLeftY();
            }
        }
        return -1;
    }

    public double getTopRightX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getTopRightX();
            }
        }
        return -1;
    }

    public double getTopRightY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getTopRightY();
            }
        }
        return -1;
    }

    public double getBottomLeftX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getBottomLeftX();
            }
        }
        return -1;
    }

    public double getBottomLeftY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getBottomLeftY();
            }
        }
        return -1;
    }

    public double getBottomRightX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getBottomRightX();
            }
        }
        return -1;
    }

    public double getBottomRightY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getBottomRightY();
            }
        }
        return -1;
    }

    public double getAngle(VisionTarget target) {
        double x = getCenterX(target);
        double y = getCenterY(target);
        if (x < VisionConstants.width / 2) {
            x = -x;
        }
        if (y < VisionConstants.height / 2) {
            y = -y;
        }
        return Math.toDegrees(Math.atan(x / y));
    }

    public double getYaw(VisionTarget target) {
        return (convertToPositionCoordinatesX(target) / 2) * VisionConstants.cameraHorizontalFOV;
    }

    public double convertToPositionCoordinatesX(VisionTarget target) {
        return (getCenterX(target) - VisionConstants.cameraResolution / 2) / 2;
    }

    public double convertToPositionCoordinatesY(VisionTarget target) {
        return -(getCenterY(target) - VisionConstants.cameraResolution / 2) / 2;
    }

    public double getPitch(VisionTarget target) {
        return convertToPositionCoordinatesY(target) / 2 * VisionConstants.cameraVerticalFOV;
    }

    public double getDistance(VisionTarget target, double targetHeight) {
        double heightDifference = Math.abs(targetHeight - VisionConstants.cameraHeight);
        double pitchRad = getPitch(target) * (Math.PI / 180);
        double headingRad = getAngle(target) * (Math.PI / 180);
        double angle = Math.tan(VisionConstants.cameraAngleRad + pitchRad) * Math.cos(headingRad);
        return heightDifference / angle;
    }

    public double getHorizontalDistance(VisionTarget target, double targetHeight) {
        double horizontalDistance;
        double distance;
        double heading = getAngle(target);
        double headingRad = heading * (Math.PI / 180);
        if (heading == 0) {
            horizontalDistance = 0;
            return horizontalDistance;
        } else {
            distance = getDistance(target, targetHeight);
            horizontalDistance = Math.cos(headingRad) * distance;
            return horizontalDistance;
        }
    }

    public double getVerticalDistance(VisionTarget target, double targetHeight) {
        double verticalDistance;
        double distance;
        double heading = getAngle(target);
        double headingRad = heading * (Math.PI / 180);
        distance = getDistance(target, targetHeight);
        verticalDistance = Math.sin(headingRad) * distance;
        return verticalDistance;
    }

}
