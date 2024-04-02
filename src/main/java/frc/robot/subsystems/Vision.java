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
        NOTE(0),
        APR1(1),
        APT2(2),
        APT3(3),
        APT4(4),
        APT5(5),
        APT6(6),
        APT7(7),
        APT8(8),
        APT9(9),
        APT10(10),
        APT11(11),
        APT12(12),
        APT13(13),
        APT14(14),
        APT15(15),
        APT16(16);

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
    private List<VisionData> visionData = new ArrayList<VisionData>();

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
        streamCam0Publisher.set(true);
        enabled0Publisher.set(false);
        enabled1Publisher.set(false);
        recordingPublisher.set(false);
    }

    @Override
    public void periodic() {
        String value = valuesSubscriber.get();
        try {
            JSONArray unparsedData = (JSONArray) jsonParser.parse(value);
            visionData.clear();
            for (Object data : unparsedData) {
                JSONObject jsonObject = (JSONObject) data;
                VisionData mappedData = new VisionData((String) jsonObject.get("x"), (String) jsonObject.get("y"),
                        (String) jsonObject.get("id"));
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

    public double getX(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getX();
            }
        }
        return -1;
    }

    public double getY(VisionTarget target) {
        for (int i = 0; i < visionData.size(); i++) {
            if (visionData.get(i).getTarget() == target) {
                return visionData.get(i).getY();
            }
        }
        return -1;
    }

    public double getAngle(VisionTarget target) {
        double x = getX(target);

        if (x == -1) {
            return 0;
        }

        x = x - (VisionConstants.width / 2);

        return x / (VisionConstants.width / (VisionConstants.fov / 2));
    }
}
