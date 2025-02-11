package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.teamcode.Automotons2425.AprilTag;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.CameraPositionFinder2425;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class getCameraPosition implements Action2425 {
    CameraPositionFinder2425 positionFinder;
    AprilTag[] aprilTags;
    double startCameraPos;

    public getCameraPosition(CameraPositionFinder2425 cPositionFinder/*, Servo cameraServo, Camera camera*/){
        positionFinder = cPositionFinder;
        //cameraServo.setDirection(Servo.Direction.FORWARD);
    }

    //todo: idk how to do this
    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void doAction() {
        if(false){

        }
        positionFinder.useCameraPositon();
    }
}
