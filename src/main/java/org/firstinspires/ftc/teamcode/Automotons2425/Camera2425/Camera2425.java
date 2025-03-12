package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Camera2425;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.TagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class Camera2425 {
    private double servoPosition;
    private double startPosition;
    private double endPosition;
    private boolean isSweepFinishedVar;
    private AprilTagProcessor aprilTagProcessor;
    private Servo servo;
    private double speed;
    public static final double DEFAULT_SPEED = 0.0004;
    public static final double DEFAULT_START_POS = 0;
    public static final double DEFAULT_END_POS = 1;
    private ArrayList<TagDetection> tagDetections;
    public Camera2425(AprilTagProcessor aprilTagProcessor, Servo servo, double startPosition, double endPosition, double speed) {
        this.aprilTagProcessor = aprilTagProcessor;
        this.servo = servo;
        servoPosition = startPosition;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.speed = speed;
       //servo.setPosition(startPosition);
        tagDetections = new ArrayList<>();
        isSweepFinishedVar = false;
    }
    public static Camera2425 defaultCamera (AprilTagProcessor aprilTagProcessor, Servo servo) {
        return new Camera2425(aprilTagProcessor, servo, DEFAULT_START_POS, DEFAULT_END_POS, DEFAULT_SPEED);
    }
    public void clearSeen() {
        tagDetections.clear();
    }
    private ArrayList<AprilTagDetection> getSeen() {
        return aprilTagProcessor.getDetections();
    }
    public ArrayList<TagDetection> getTags(){
        return tagDetections;
    }
    /*public void setServoPosition(double position){
        servoPosition = position;
    }*/
    public void update (int timeCoeff) {
        // grab tags
        ArrayList<AprilTagDetection> currentDetections = getSeen();
        for (AprilTagDetection detection : currentDetections) {
            tagDetections.add(new TagDetection(detection, servoPosition));
        }
        // move servo
        servoPosition += speed * timeCoeff;
        servo.setPosition(servoPosition);
        if(servoPosition >endPosition){
            isSweepFinishedVar = true;
        }
    }
    public void startSweep () {
        clearSeen();
        servo.setPosition(startPosition);
    }
    public boolean isSweepFinished () {
        return isSweepFinishedVar;
    }

}