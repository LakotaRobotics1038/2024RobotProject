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
        NOTES(17);

        public final int value;

        VisionTarget(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
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
                VisionData mappedData = new VisionData((String) jsonObject.get("x"), (String) jsonObject.get("y"),
                        (String) jsonObject.get("area"), (String) jsonObject.get("conf"),
                        (String) jsonObject.get("id"), (String) jsonObject.get("xDistance"),
                        (String) jsonObject.get("yDistance"));
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

    public double getX(int id) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getID() == id) {
                return visionData.get(i).getX();
            }
        }
        return -1;
    }

    public double getY(int id) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getID() == id) {
                return visionData.get(i).getY();
            }
        }
        return -1;
    }

    public double getArea(int id) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getID() == id) {
                return visionData.get(i).getArea();
            }
        }
        return -1;
    }

    public double getXDistance(int id) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getID() == id) {
                return visionData.get(i).getXDistance();
            }
        }
        return 0;
    }

    public double getYDistance(int id) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getID() == id) {
                return visionData.get(i).getYDistance();
            }
        }
        return 0;
    }

    public double getAngle(int id) {
        double x = getX(id);
        double y = getY(id);
        if (x < VisionConstants.width / 2) {
            x = -getX(id);
        }
        if (y < VisionConstants.height / 2) {
            y = -y;
        }
        return Math.toDegrees(Math.atan(x / y));
    }

    public double getDistance(int id) {
        double y = getY(id);
        double oldY = 0.0;

        if (id == VisionTarget.NOTES.value) {
            if (y > oldY) {
                oldY = y;
                return 0;
            } else {
                oldY = 0.0;
                return -1;
            }

        } else if (id >= VisionTarget.APR1.value && id <= VisionTarget.APT16.value) {
            double area = getArea(id);
            return area;
        } else {
            return 1;
        }
    }

    public ArrayList<Double> getRelativeDistanceFromObject(int id) {
        double distance = Math.sqrt((getXDistance(id) * getXDistance(id)) + (getYDistance(id) * getYDistance(id)));
        double angle = Math.tan(getYDistance(id) / getXDistance(id));

        ArrayList<Double> values = new ArrayList<>();
        values.add(distance);
        values.add(angle);
        return values;

    }

}
