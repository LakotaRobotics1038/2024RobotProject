package frc.robot.utils;

import frc.robot.subsystems.Vision.VisionTarget;

public class VisionData {
    private double x;
    private double y;
    private VisionTarget target;

    public VisionData(String x, String y, String target) {
        System.out.println("x " + x + " y " + y);
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.target = VisionTarget.values()[Integer.parseInt(target)];
    }

    @Override
    public String toString() {
        return "x: " + this.x + " y: " + this.y + " area: " + " tgt: " + this.target;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public VisionTarget getTarget() {
        return target;
    }
}
