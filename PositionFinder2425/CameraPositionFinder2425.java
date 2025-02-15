package org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.Camera2425.Camera2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class CameraPositionFinder2425 extends PositionFinder2425{
    private boolean doingSweep;
    private Camera2425 camera;
    private ElapsedTime runtime;
    private int lastMilli;
    public CameraPositionFinder2425(DriveTrain2425 driveTrain, Position position, Camera2425 camera) {
        super(driveTrain, position);
        this.camera = camera;
        doingSweep = false;
        runtime = new ElapsedTime();
        lastMilli = (int) runtime.milliseconds();
    }
    //todo: Anton, this is where you will add your calculation code
    //todo: add camera info in the paramaters
    private Position cameraCalc(ArrayList<TagDetection> seenTags) {
        Position callThisWhateverYouWantAnton = new Position(0, 0, 0);
        return callThisWhateverYouWantAnton;
    }
    public void useCameraPositon(ArrayList<TagDetection> seenTags){
        currentPosition = cameraCalc(seenTags);
    }
    public void startSweep () {
        doingSweep = true;
        camera.startSweep();
        lastMilli = (int) runtime.milliseconds();
    }
    public void updatePosition () {
        Position changeInPosition = driveTrain.positionChange();
        adjustPositionRobotSpace(changeInPosition.aPos, changeInPosition.bPos, changeInPosition.facingDirection);
        if(doingSweep){
            int elapsedMilli = (int) runtime.milliseconds()-lastMilli;
            lastMilli = (int) runtime.milliseconds();
            camera.update(elapsedMilli);
            if(camera.isSweepFinished()){
                useCameraPositon(camera.getTags());
                doingSweep = false;
            }
        }
    }
}
