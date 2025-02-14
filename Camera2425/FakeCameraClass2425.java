package org.firstinspires.ftc.teamcode.Automotons2425.Camera2425;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class FakeCameraClass2425 {
    public double servoPosition;
    private AprilTagProcessor aprilTag;

    public void clearSeen() {

    }
    public FakeCameraClass2425(){
        servoPosition = 0;
    }

    public  List<AprilTagDetection> getSeen() {
        List<AprilTagDetection> seenTags = aprilTag.getDetections();
        return seenTags;
    }
    public void setServoPosition(double position){
        servoPosition = position;
    }

}