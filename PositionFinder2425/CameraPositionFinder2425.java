package org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.AprilTag;
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
    private ArrayList<AprilTag> aprilTags;
    public CameraPositionFinder2425(DriveTrain2425 driveTrain, Position position, Camera2425 camera) {
        super(driveTrain, position);
        this.camera = camera;
        doingSweep = false;
        runtime = new ElapsedTime();
        lastMilli = (int) runtime.milliseconds();
        aprilTags = AprilTag.getDefaultArena2425();
    }
    private Position cameraCalc(ArrayList<TagDetection> seenTags) {
        /*ArrayList<TagDetection> uniqueTags = filterDetections(seenTags);
        Position toReturn = new Position(0,0,0);
        if(uniqueTags.size()>1){
            TagDetection uniqueTag1 = uniqueTags.get(0);
            AprilTag tag1 = findAprilTag(uniqueTag1.id);
            TagDetection uniqueTag2 = uniqueTags.get(1);
            AprilTag tag2 = findAprilTag(uniqueTag1.id);
            //this site says that this is the math. idk
            //https://math.stackexchange.com/questions/256100/how-can-i-find-the-points-at-which-two-circles-intersect#:~:text=The%20intersection%20points%20are%20given,the%20centers%20of%20the%20circles.
            //(x,y)=12(x1+x2,y1+y2)+r21−r222R2(x2−x1,y2−y1)±12√2r21+r22R2−(r21−r22)2R4−1(y2−y1,x1−x2)
            //and solving for each var separately
            //𝑥𝑦=𝑙𝑑(𝑥2−𝑥1)±ℎ𝑑(𝑦2−𝑦1)+𝑥1,=𝑙𝑑(𝑦2−𝑦1)∓ℎ𝑑(𝑥2−𝑥1)+𝑦1.



        }*/

        return null;
    }
    private AprilTag findAprilTag(int tagID){
        for(AprilTag tag: aprilTags){
            if(tag.getId() == tagID){
                return tag;
            }
        }

        return null;
    }

    /**
     * go through the arraylist of tag detections and return the first one of each id in an arraylist
     * @return
     /
    private ArrayList<TagDetection> filterDetections(ArrayList<TagDetection> tagsSeen) {
        ArrayList<TagDetection> uniqueTags = new ArrayList<TagDetection>();
        for (TagDetection tag : tagsSeen) {
            boolean isAlreadySeen = false;
            for (TagDetection seenTag : uniqueTags) {
                if (tag.id == seenTag.id) {
                    isAlreadySeen = true;
                }
            }
            if (!isAlreadySeen) {
                uniqueTags.add(tag);
            }
        }
        return uniqueTags;
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