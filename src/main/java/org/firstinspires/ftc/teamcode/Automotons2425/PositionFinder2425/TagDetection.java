package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class TagDetection {
    public final int id;
    public final double bearing;
    public final double distance;
    public final double servoAngle;
    public TagDetection (AprilTagDetection detection, double angle) {
        id = detection.id;
        bearing = detection.ftcPose.bearing;
        distance = detection.ftcPose.range;
        this.servoAngle = angle;
    }
}
