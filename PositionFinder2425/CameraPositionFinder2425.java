package org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;

import org.firstinspires.ftc.teamcode.Automotons2425.Camera2425.Camera2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class CameraPositionFinder2425 extends PositionFinder2425{
    private Camera2425 camera;
    public CameraPositionFinder2425(DriveTrain2425 driveTrain, Position position, Camera2425 camera) {
        super(driveTrain, position);
        this.camera = camera;
    }
    //todo: Anton, this is where you will add your calculation code
    //todo: add camera info in the paramaters
    private Position cameraCalc(List<AprilTagDetection> seenTags) {
        Position callThisWhateverYouWantAnton = new Position(0, 0, 0);
        return callThisWhateverYouWantAnton;
    }
    public void useCameraPositon(List<AprilTagDetection> seenTags){
        currentPosition = cameraCalc(seenTags);
    }
    public void startSweep () {

    }
}
