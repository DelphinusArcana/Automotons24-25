package org.firstinspires.ftc.teamcode.Automotons2425.OldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.Automotons2425.AprilTag;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// It seems to think 12 real inches is 13 inches -- probably because the tags are a little larger than 2"
// The play area is about 94" across.
// The tags are 3.5" in from the edges

@Autonomous(name = "MotorControl")
public class AprilTagNavWithMotorControl extends LinearOpMode
{
    /** What the program uses to interact with the webcam */
    private VisionPortal visionPortal;
    /** Finds the april tags in the camera's feed */
    private AprilTagProcessor aprilTag;
    /** The robot's believed position */
    private Position position;
    private ArrayList<Position> last10Poses;
    /** The the tags that the robot will interact with */
    private ArrayList<AprilTag> tags;
    /** Whether or not the camera currently sees any april tags from tags */
    private boolean goodTagsSeen;
    /** Multiply by this to convert from degrees (fake) to radians (real) */
    private static final double DEG_TO_RAD = Math.PI / 180.0;
    /** The motor that powers the left front drive wheel */
    private MotorControl leftFrontDrive;
    /** The motor that powers the left rear drive wheel */
    private MotorControl leftRearDrive;
    /** The motor that powers the right rear drive wheel */
    private MotorControl rightRearDrive;
    /** The motor that powers the right front drive wheel */
    private MotorControl rightFrontDrive;
    /** The position of the left front drive motor when the opMode began */
    private int leftFrontFirstPos;
    /** The position of the left rear drive motor when the opMode began */
    private int leftRearFirstPos;
    /** The position of the right rear drive motor when the opMode began */
    private int rightRearFirstPos;
    /** The position of the right front drive motor when the opMode began */
    private int rightFrontFirstPos;
    public static final double INCHES_PER_MOTOR_POS = 1.0 / 25.0;
    /** The approximate angle (in radians because degrees are fake) the robot moves for every unit of motor position*/
    public static final double RADIANS_PER_MOTOR_POS = Math.PI / 1000.0;
    /** The minimum forward power to send to the motors when moving at all */
    private double minPower;
    /** The maximum forward power to send to the motors */
    private double maxPower;
    /** The distance from the target at which the robot will use full forward power */
    private double maxPowerDistance;
    /** Modifies how fast the robot will rotate */
    private double rotatePowerMod;
    /** The difference in angles between the desired and real angle at which the robot will not attempt to correct its angle */
    private double goodEnoughAngle;
    /** The distance from the target at which the robot will not attempt to correct its position (neither by rotating nor by moving) */
    private double goodEnoughDistance;
    /** The total amount the robot has rotated */
    private double totalRotateDistance;
    /** True if the robot should turn counterclockwise when facing almost directly away from the intended position, false if it should turn clockwise */
    private boolean turningCCW;
    /** True allows the robot to send power to the motors, false prevents it from doing so */
    private boolean movement;
    /** Determines in which direction positive power spins a motor*/
    public static final DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    /** Determines in which direction positive power spins a motor*/
    public static final DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;
    /** The position at the origin of the coordinate system */
    public static final Position origin = new Position (0.0, 0.0, 0.0);
    public static final double distanceMod = 1.0;
    /** Initializes all instance variables. Carries out the robot's actions. */
    @Override
    public void runOpMode() {
        position = new Position (0.0, 0.0, 0.0);
        last10Poses = new ArrayList<Position>();
        last10Poses.add(position);
        minPower = 0.25;
        maxPower = 0.5;
        maxPowerDistance = 24.0;
        rotatePowerMod = 1.5;
        goodEnoughAngle = Math.PI / 30;
        goodEnoughDistance = 3.0;
        goodTagsSeen = false;
        totalRotateDistance = 0.0;
        movement = false;
        turningCCW = true;

        boolean aPressed = false;
        boolean bPressed = false;

        leftFrontDrive = new MotorControl(hardwareMap.get(DcMotor.class, "leftFrontDrive"), reverse);
        leftRearDrive = new MotorControl(hardwareMap.get(DcMotor.class, "leftRearDrive"), forward);
        rightRearDrive = new MotorControl(hardwareMap.get(DcMotor.class, "rightRearDrive"), forward);
        rightFrontDrive = new MotorControl(hardwareMap.get(DcMotor.class, "rightFrontDrive"),forward);

        // Initialize the Apriltag Detection process
        initAprilTag();

        tags = new ArrayList<AprilTag>();
        tags.add(new AprilTag(2, 0.0, 43.5, Math.PI));
        tags.add(new AprilTag(4, -43.5, 0.0, 3 * Math.PI / 2));
        tags.add(new AprilTag(5, 43.5, 0.0, Math.PI / 2));
        tags.add(new AprilTag(8, 0.0, -43.5, 0.0));
        tags.add(new AprilTag(1, -43.5, 43.5, 5 * Math.PI / 4));
        tags.add(new AprilTag(3, 43.5, 43.5, 3 * Math.PI / 4));
        tags.add(new AprilTag(6, -43.5, -43.5, 7 * Math.PI / 4));
        tags.add(new AprilTag(9, 43.5, -43.5, Math.PI / 4));

        setManualExposure(20, 4000);  // Use low exposure time to reduce motion blur

        // Wait for driver to press start
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        updateMotors();
        leftFrontFirstPos = leftFrontDrive.getPos();
        leftRearFirstPos = leftRearDrive.getPos();
        rightRearFirstPos = rightRearDrive.getPos();
        rightFrontFirstPos = rightFrontDrive.getPos();

        while (opModeIsActive()) {
            updatePosition();

            if (gamepad1.a && !aPressed) {
                movement = !movement;
                aPressed = true;
            } else if (!gamepad1.a) {
                aPressed = false;
            }
            if (gamepad1.b && !bPressed) {
                bPressed = true;
            } else if (!gamepad1.b) {
                bPressed = false;
            }

            move();

            telemetry.update();
        }
    }
    /** Updates the believed position of the robot. If there are april tags in view, uses them to determine the position.
     * Otherwise, uses feedback from the motors to determine how far the robot moved since the last time it was called. */
    public void updatePosition () {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        goodTagsSeen = false;
        StringBuilder seenTags = new StringBuilder();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                seenTags.append(detection.id);
                seenTags.append(", ");
                for (AprilTag tag : tags) {
                    if (detection.id == tag.getId()) {
                        last10Poses.add(tag.robotPosition(detection.ftcPose.x * distanceMod, detection.ftcPose.y * distanceMod, detection.ftcPose.yaw * DEG_TO_RAD));
                        if (last10Poses.size() > 10)
                            last10Poses.remove(0);
                        goodTagsSeen = true;
                        //telemetry.addData("Used tag", detection.id);
                    }
                }
            } else {
                //telemetry.addLine("null metadata");
            }
        }

        telemetry.addData("Number of tags in view", currentDetections.size());
        telemetry.addData("Tags", seenTags);

        updateMotors();

        if (!goodTagsSeen){
            double radsTurned = rotateDistance();
            double avgDirection = position.facingDirection + radsTurned / 2;
            double forwardDist = forwardDistance();
            for (Position pos : last10Poses) {
                pos.aPos += forwardDist * Math.cos(avgDirection);
                pos.bPos += forwardDist * Math.sin(avgDirection);
                pos.facingDirection += radsTurned;
            }
        }
        position = Position.medianPos(last10Poses);
        totalRotateDistance += rotateDistance() / RADIANS_PER_MOTOR_POS;

        telemetry.addData("Believed Position", position);
        telemetry.addData("Size", last10Poses.size());
        telemetry.addData("Poses", last10Poses);
        telemetry.addData("total rotate distance", totalRotateDistance);
        telemetry.addData("better total", rotateDistSinceStart());


        for (AprilTagDetection tag : currentDetections){
            if (tag.metadata != null) {
                telemetry.addData("Found", "ID %d (%s)", tag.id, tag.metadata.name);
                telemetry.addData("Range",  "%5.1f inches", tag.ftcPose.range * distanceMod);
                telemetry.addData("Bearing","%3.0f degrees", tag.ftcPose.bearing);
                telemetry.addData("Yaw","%3.0f degrees", tag.ftcPose.yaw);
            }
        }
    }
    /** Updates each of the drive motors */
    public void updateMotors () {
        leftFrontDrive.update();
        leftRearDrive.update();
        rightRearDrive.update();
        rightFrontDrive.update();
    }
    // in inches
    /** Determines how far forward the robot has moved (in inches) since the last time updatePosition() was called
     * @return the forward distance the robot has moved (in inches) since the last time updatePosition() was called */
    public double forwardDistance () {
        double dist = 0;
        dist += leftFrontDrive.distanceTraveled();
        dist += leftRearDrive.distanceTraveled();
        dist += rightRearDrive.distanceTraveled();
        dist += rightFrontDrive.distanceTraveled();
        dist /= 4.0; // average of the distances
        return dist * INCHES_PER_MOTOR_POS;
    }
    /** Determines how far the robot has turned (in radians because degrees are fake) since the last time updatePosition() was called
     * @return the angle the robot has turned (in radians because degrees are fake) since the last time updatePosition() was called */
    public double rotateDistance () {
        double dist = 0;
        dist -= leftFrontDrive.distanceTraveled();
        dist -= leftRearDrive.distanceTraveled();
        dist += rightRearDrive.distanceTraveled();
        dist += rightFrontDrive.distanceTraveled();
        dist /= 4.0; // average of the distances
        return dist * RADIANS_PER_MOTOR_POS;
    }
    /** Determines how far the robot has turned (in radians) since the start
     * @return the angle the robot has turned (in radians) since the start */
    public double rotateDistSinceStart () {
        double dist = 0;
        dist -= leftFrontDrive.getPos() - leftFrontFirstPos;
        dist -= leftRearDrive.getPos() - leftRearFirstPos;
        dist += rightRearDrive.getPos() - rightRearFirstPos;
        dist += rightFrontDrive.getPos() - rightFrontFirstPos;
        dist /= 4.0; // average of the distances
        return dist;
    }
    /** Turns and moves the robot toward the origin */
    public void move () {
        double desiredAngle = position.angleTo(origin);
        telemetry.addData("Angle to origin", desiredAngle);
        double diff = position.angleDiff(origin);
        telemetry.addData("Angle diff", diff);
        telemetry.addData("Max power", maxPower);

        if (position.distanceTo(origin) > goodEnoughDistance && movement) {
            telemetry.addData("In", "if");
            if (Math.abs (diff) > goodEnoughAngle) {
                if (Math.abs(diff) >= 3 * Math.PI / 4) {
                    if (turningCCW) {
                        rotate(Math.abs(diff * rotatePowerMod));
                    } else {
                        rotate(-1.0 * Math.abs(diff * rotatePowerMod));
                    }
                } else {
                    turningCCW = position.angleDiff(origin) > 0;
                    rotate(diff * rotatePowerMod);
                }
            }
            if (Math.abs(diff) < Math.PI / 4) {
                double powerSlope = (maxPower - minPower)/(maxPowerDistance - goodEnoughDistance);
                forward(powerSlope * (position.distanceTo(origin) - maxPowerDistance) + maxPower);
            }
        }
    }
    /** Rotates the robot.
     * @param power the power to send to the wheels. Positive power turns counterclockwise (increases angle)
     * */
    public void rotate (double power) {
        if (Math.abs(power) + maxAbsDrivePower() > maxPower) {
            power = Math.signum(power) * (maxPower - maxAbsDrivePower());
        }
        telemetry.addData("power", power);
        leftFrontDrive.addPower(-1 * power);
        leftRearDrive.addPower(-1 * power);
        rightRearDrive.addPower(power);
        rightFrontDrive.addPower(power);
    }
    /** Moves the robot forward.
     * @param power the power to send to the wheels.
     * */
    public void forward (double power) {
        if (Math.abs(power) + maxAbsDrivePower() > maxPower) {
            power = Math.signum(power) * (maxPower - maxAbsDrivePower());
        }
        leftFrontDrive.addPower(power);
        leftRearDrive.addPower(power);
        rightRearDrive.addPower(power);
        rightFrontDrive.addPower(power);
    }
    /** Calculates the maximum absolute value of the power sent to each motor
     * @return the maximum absolute value of the power sent to each motor*/
    public double maxAbsDrivePower () {
        double leftMax = Math.max(Math.abs(leftFrontDrive.getPower()), Math.abs(leftRearDrive.getPower()));
        double rightMax = Math.max(Math.abs(rightRearDrive.getPower()), Math.abs(rightFrontDrive.getPower()));
        return Math.max(leftMax, rightMax);
    }
    /** Initializes the AprilTag processor.
     * Note: this code comes from the examples provided by FTC.
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
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    /** Manually sets the camera gain and exposure.
     * This can only be called AFTER calling initAprilTag(), and only works for Webcams
     * Note: this code comes from the examples provided by FTC
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
