package frc.robot.utils;

import frc.robot.subsystems.Vision.VisionTarget;

public class VisionData {
    private double centerX;
    private double centerY;
    private double topLeftX;
    private double topLeftY;
    private double topRightX;
    private double topRightY;
    private double bottomLeftX;
    private double bottomLeftY;
    private double bottomRightX;
    private double bottomRightY;
    private double confidence;
    private VisionTarget target;

    public VisionData(String centerX, String centerY, String topLeftX, String topLeftY, String topRightX,
            String topRightY, String bottomLeftX, String bottomLeftY, String bottomRightX, String bottomRightY,
            String confidence, String target) {
        this.centerX = Double.parseDouble(centerX);
        this.centerY = Double.parseDouble(centerY);
        this.topLeftX = Double.parseDouble(topLeftX);
        this.topLeftY = Double.parseDouble(topLeftY);
        this.topRightX = Double.parseDouble(topRightX);
        this.topRightY = Double.parseDouble(topRightY);
        this.bottomLeftX = Double.parseDouble(bottomLeftX);
        this.bottomLeftY = Double.parseDouble(bottomLeftY);
        this.bottomRightX = Double.parseDouble(bottomRightX);
        this.bottomRightY = Double.parseDouble(bottomRightY);
        this.confidence = Double.parseDouble(confidence);
        this.target = VisionTarget.values()[Integer.parseInt(target)];
    }

    public VisionData(String centerX, String centerY, String confidence, String target) {
        this.centerX = Double.parseDouble(centerX);
        this.centerY = Double.parseDouble(centerY);
        this.confidence = Double.parseDouble(confidence);
        this.target = VisionTarget.values()[Integer.parseInt(target)];
    }

    public String toStringAprilTag() {
        return "Center X: " + this.centerX + " Center Y: " + this.centerY + " TL X: " + this.topLeftX + " TL Y: "
                + this.topLeftY + " TR X: " + this.topRightX + " TR Y: " + this.topRightY + " BL X: " + this.bottomLeftX
                + " BL Y: " + this.bottomLeftY + " BR X: " + this.bottomRightX + " BR Y: " + this.bottomRightY
                + " Conf: " + this.confidence + " Tgt: "
                + this.target;
    }

    public String toStringNote() {
        return " Center X: " + this.centerX + " Center Y: " + this.centerY + " Conf: " + this.confidence + " Tgt: "
                + this.target;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getTopLeftX() {
        return topLeftX;
    }

    public double getTopLeftY() {
        return topLeftY;
    }

    public double getTopRightX() {
        return topRightX;
    }

    public double getTopRightY() {
        return topRightY;
    }

    public double getBottomLeftX() {
        return bottomLeftX;
    }

    public double getBottomLeftY() {
        return bottomLeftY;
    }

    public double getBottomRightX() {
        return bottomRightX;
    }

    public double getBottomRightY() {
        return bottomRightY;
    }

    public VisionTarget getTarget() {
        return target;
    }
}
