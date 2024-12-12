package org.firstinspires.ftc.teamcode.Automotons2425.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.Automotons2425.AprilTag;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * This OpMode illustrates using a camera to locate and drive towards a specific AprilTag.
 * The code assumes a Holonomic (Mecanum or X Drive) Robot.
 *
 * For an introduction to AprilTags, see the ftc-docs link below:
 * https://ftc-docs.firstinspires.org/en/latest/apriltag/vision_portal/apriltag_intro/apriltag-intro.html
 *
 * When an AprilTag in the TagLibrary is detected, the SDK provides location and orientation of the tag, relative to the camera.
 * This information is provided in the "ftcPose" member of the returned "detection", and is explained in the ftc-docs page linked below.
 * https://ftc-docs.firstinspires.org/apriltag-detection-values
 *
 * The drive goal is to rotate to keep the Tag centered in the camera, while strafing to be directly in front of the tag, and
 * driving towards the tag to achieve the desired distance.
 * To reduce any motion blur (which will interrupt the detection process) the Camera exposure is reduced to a very low value (5mS)
 * You can determine the best Exposure and Gain values by using the ConceptAprilTagOptimizeExposure OpMode in this Samples folder.
 *
 * The code assumes a Robot Configuration with motors named: leftFrontDrive and rightFrontDrive, leftRearDrive and rightRearDrive.
 * The motor directions must be set so a positive power goes forward on all wheels.
 * This sample assumes that the current game AprilTag Library (usually for the current season) is being loaded by default,
 * so you should choose to approach a valid tag ID (usually starting at 0)
 *
 * Under manual control, the left stick will move forward/back & left/right.  The right stick will rotate the robot.
 * Manually drive the robot until it displays Target data on the Driver Station.
 *
 * Press and hold the *Left Bumper* to enable the automatic "Drive to target" mode.
 * Release the Left Bumper to return to manual driving mode.
 *
 * Under "Drive To Target" mode, the robot has three goals:
 * 1) Turn the robot to always keep the Tag centered on the camera frame. (Use the Target Bearing to turn the robot.)
 * 2) Strafe the robot towards the centerline of the Tag, so it approaches directly in front  of the tag.  (Use the Target Yaw to strafe the robot)
 * 3) Drive towards the Tag to get to the desired distance.  (Use Tag Range to drive the robot forward/backward)
 *
 * Use DESIRED_DISTANCE to set how close you want the robot to get to the target.
 * Speed and Turn sensitivity can be adjusted using the SPEED_GAIN, STRAFE_GAIN and TURN_GAIN constants.
 *
 * Use Android Studio to Copy this Class, and Paste it into the TeamCode/src/main/java/org/firstinspires/ftc/teamcode folder.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 *
 */

// It seems to think 12 real inches is 13 inches -- probably because the tags are a little larger than 2"

@Autonomous(name = "RotationTest")
public class RotationTest extends LinearOpMode
{
    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private double facingDirection; // TODO: fix
    private double power;
    private ArrayList<AprilTag> tags;
    private static final double DEG_TO_RAD = Math.PI / 180.0;
    private DcMotor leftFrontDrive;
    private DcMotor leftRearDrive;
    private DcMotor rightRearDrive;
    private DcMotor rightFrontDrive;
    private int leftFrontPos;
    private int leftRearPos;
    private int rightRearPos;
    private int rightFrontPos;
    private double startTime;
    private boolean running;
    private double stoppedRotatePos;
    private double stoppedTime;
    public static final DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    public static final DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;
    @Override
    public void runOpMode() {

        facingDirection = 0.0;
        power = 0.5;
        running = true;
        stoppedRotatePos = 0.0;
        stoppedTime = 0.0;

        boolean aPressed = false;
        boolean bPressed = false;

        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftRearDrive = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rightRearDrive = hardwareMap.get(DcMotor.class, "rightRearDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");

        leftFrontDrive.setDirection(reverse);
        leftRearDrive.setDirection(forward);
        rightRearDrive.setDirection(forward);
        rightFrontDrive.setDirection(forward);

        // Initialize the Apriltag Detection process
        initAprilTag();

        tags = new ArrayList<AprilTag>();
        tags.add(new AprilTag(6, 0.0, 0.0, 0.0)); // ID 6 is the origin here
        tags.add(new AprilTag(8, 8.0, 0.0, 3 * Math.PI / 2));
        tags.add(new AprilTag(9, 24.0, 0.0, 0.0));

        if (USE_WEBCAM)
            setManualExposure(6, 250);  // Use low exposure time to reduce motion blur

        // Wait for driver to press start
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        waitForStart();

        startTime = System.currentTimeMillis();
        leftFrontPos = leftFrontDrive.getCurrentPosition();
        leftRearPos = leftRearDrive.getCurrentPosition();
        rightRearPos = rightRearDrive.getCurrentPosition();
        rightFrontPos = rightFrontDrive.getCurrentPosition();

        while (opModeIsActive()) {
            // Step through the list of detected tags and look for a matching tag
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();

            StringBuilder seenTags = new StringBuilder();
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    seenTags.append(detection.id);
                    seenTags.append(", ");
                    updatePosition(detection);
                } else {
                    telemetry.addLine("null metadata");
                }
            }

            if (running) {
                telemetry.addData("Time", System.currentTimeMillis() - startTime);
                telemetry.addData("Power", power);
                telemetry.addData("Rotated", rotateDistance());
            } else {
                telemetry.addData("Time elapsed", stoppedTime);
                telemetry.addData("Power", power);
                telemetry.addData("Rotated", stoppedRotatePos);
            }
            move();

            telemetry.update();

            if (gamepad1.a && !aPressed) {
                power -= 0.02;
                aPressed = true;
            } else if (!gamepad1.a) {
                aPressed = false;
            }
            if (gamepad1.b && !bPressed) {
                power += 0.02;
                bPressed = true;
            } else if (!gamepad1.b) {
                bPressed = false;
            }
            if (gamepad1.x) {
                running = true;
                startTime = System.currentTimeMillis();
                leftFrontPos = leftFrontDrive.getCurrentPosition();
                leftRearPos = leftRearDrive.getCurrentPosition();
                rightRearPos = rightRearDrive.getCurrentPosition();
                rightFrontPos = rightFrontDrive.getCurrentPosition();
            }
            if (gamepad1.y) {
                running = false;
                stoppedRotatePos = rotateDistance();
                stoppedTime = System.currentTimeMillis() - startTime;
            }
            telemetry.addData("running", running);
            sleep(10);
        }
    }
    public double rotateDistance () {
        double dist = 0;
        dist -= leftFrontDrive.getCurrentPosition() - leftFrontPos;
        dist -= leftRearDrive.getCurrentPosition() - leftRearPos;
        dist += rightRearDrive.getCurrentPosition() - rightRearPos;
        dist += rightFrontDrive.getCurrentPosition() - rightFrontPos;
        dist /= 4.0; // average of the distances
        return (dist * AprilTagNav.RADIANS_PER_MOTOR_POS)/AprilTagNav.RADIANS_PER_MOTOR_POS;
    }
    private void updatePosition (AprilTagDetection detection) {
        for (AprilTag tag : tags) {
            if (detection.id == tag.getId()) {
                facingDirection = tag.robotDirection(detection.ftcPose.yaw * DEG_TO_RAD);
            }
        }
    }
    /*private String posString () {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("(");
        String aString = "" + aPos;
        if (aString.length() < 4)
            toReturn.append(aString);
        else
            toReturn.append(aString.substring(0,4));
        toReturn.append(",");
        String bString = "" + bPos;
        if (bString.length() < 4)
            toReturn.append(bString);
        else
            toReturn.append(bString.substring(0,4));
        toReturn.append(")");
        toReturn.append(", pointing ");
        String facingString = "" + facingDirection;
        if (facingString.length() < 4)
            toReturn.append(facingString);
        else
            toReturn.append(facingString.substring(0,4));
        return toReturn.toString();
    }*/
    // currently just goes to the origin (not even that yet)
    public void move () {
        leftFrontDrive.setPower(0.0);
        leftRearDrive.setPower(0.0);
        rightRearDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        if (rotateDistance() < 1000) {
            rotate(power);
        } else {
            rotate(0.0);
        }
    }
    /*public double angleToOrigin () {
        if (aPos > 0) {
            return Math.PI + Math.atan(bPos / aPos);
        } else if (aPos < 0) {
            return Math.atan(bPos / aPos);
        } else if (bPos > 0) {
            return 3 * Math.PI / 2;
        } else {
            return Math.PI / 2;
        }
    }*//*
    public double distanceToOrigin () {
        return Math.hypot(aPos, bPos);
    }*/
    /** w/ positive power, increase angle (turn ccw)
     * */
    public void rotate (double power) {
        addPower(leftFrontDrive, -1 * power);
        addPower(leftRearDrive, -1 * power);
        addPower(rightRearDrive, power);
        addPower(rightFrontDrive, power);
        telemetry.addData("power in rotate", power);
    }/*
    public void forward (double power) {
        if (Math.abs(power) + maxAbsDrivePower() > maxPower) {
            power = Math.signum(power) * (maxPower - maxAbsDrivePower());
        }
        addPower(leftFrontDrive, power);
        addPower(leftRearDrive, power);
        addPower(rightRearDrive, power);
        addPower(rightFrontDrive, power);
    }*/
    public void addPower (DcMotor motor, double power) {
        motor.setPower(motor.getPower() + power);
    }
    public double maxAbsDrivePower () {
        double leftMax = Math.max(Math.abs(leftFrontDrive.getPower()), Math.abs(leftRearDrive.getPower()));
        double rightMax = Math.max(Math.abs(rightRearDrive.getPower()), Math.abs(rightFrontDrive.getPower()));
        return Math.max(leftMax, rightMax);
    }

    /** Returns the signed difference between the two angles with the smallest absolute value
     * */
    public double angleDiff (double a1, double a2) {
        double diff = a1 - a2;
        if (diff > 0) {
            while (diff > Math.PI) {
                diff -= 2 * Math.PI;
            }
            return diff;
        } else if (diff < 0) {
            while (diff < -1 * Math.PI) {
                diff += 2 * Math.PI;
            }
            return diff;
        } else
            return diff;
    }
    /**
     * Initialize the AprilTag processor.
     */
    private void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(1);

        // Create the vision portal by using a builder.e
        if (USE_WEBCAM) {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }

    /**
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
     */
    private void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}
