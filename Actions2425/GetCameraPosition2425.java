/*package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.teamcode.Automotons2425.AprilTag;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.CameraPositionFinder2425;

public class GetCameraPosition2425 implements Action2425 {
    CameraPositionFinder2425 positionFinder;
    AprilTag[] aprilTags;
    double endCameraPos;
    double endCameraPosError;
    boolean isComplete;
    double cameraStartPos;
    boolean doSetupCamera;
    FakeCameraClass2425 camera;
    //anything involving camera is just placement
    public GetCameraPosition2425(CameraPositionFinder2425 cPositionFinder, FakeCameraClass2425 cameraArg){
        positionFinder = cPositionFinder;
        camera = cameraArg;
        //magic # represents the end position of the camera's servo
        endCameraPos = 1;
        //magic # represents error for endCameraPos
        endCameraPosError = 0.05;
        //magic # for resetting camera servo at end of action
        cameraStartPos = -1;
        isComplete = false;
        doSetupCamera = true;

    }

    //todo: idk how to do this
    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public void doAction() {
        //clear camera if it is the start of the action to remove previously seen tags with incorrect positions
        if (doSetupCamera){
            doSetupCamera = false;
            camera.clearSeen();
        }
        //checks if cameraServo has scanned all 240 or so degrees
        if(camera.servoPosition >endCameraPos-endCameraPosError){
            //TODO: this
            //positionFinder.useCameraPositon(camera.getSeen());
            isComplete = true;
            //resets camera position
            camera.setServoPosition(cameraStartPos);
        }
        else{
            camera.setServoPosition(endCameraPos);
        }
    }
}
*/