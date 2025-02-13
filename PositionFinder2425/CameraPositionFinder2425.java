package org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;

import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class CameraPositionFinder2425 extends PositionFinder2425{
    //todo: add a camera class to constructor
    public CameraPositionFinder2425(DriveTrain2425 driveTrain, Position position) {
        super(driveTrain, position);
    }
    //todo: Anton, this is where you will add your calculation code
    //todo: add camera info in the paramaters
    private Position cameraCalc(List<AprilTagDetection> seenTags) {
        Position callThisWhateverYouWantAnton = new Position(0, 0, 0);
        return callThisWhateverYouWantAnton;
    }
    //todo: this needs to take arguments but we don't have the data type for that argument
    public void useCameraPositon(List<AprilTagDetection> seenTags){
        currentPosition = cameraCalc(seenTags);
    }
}
