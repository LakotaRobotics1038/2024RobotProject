package frc.robot.utils;

import frc.robot.subsystems.Vision.VisionTarget;

public class VisionData {
    private double x;
    private double y;
    private double area;
    private double xDistance;
    private double yDistance;
    private double confidence;
    private VisionTarget target;

    public VisionData(String x, String y, String area, String confidence, String target, String xDistance,
            String yDistance) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.area = Double.parseDouble(area);
        this.confidence = Double.parseDouble(confidence);
        this.target = VisionTarget.values()[Integer.parseInt(target)];
        this.xDistance = Double.parseDouble(xDistance);
        this.yDistance = Double.parseDouble(yDistance);
    }

    @Override
    public String toString() {
        return "x: " + this.x + " y: " + this.y + " area: " + this.area + " conf: " + this.confidence + " tgt: "
                + this.target;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getID() {
        return target.getValue();
    }

    public double getArea() {
        return area;
    }

    public double getXDistance() {
        return xDistance;
    }

    public double getYDistance() {
        return yDistance;
    }
}
