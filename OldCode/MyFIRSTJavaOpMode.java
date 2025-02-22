package org.firstinspires.ftc.teamcode.Automotons2425.OldCode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/** The TeleOp we used in the 2023-2024 season*/
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name="Template: 2024 Brie 4", group="Linear Opmode")
public class MyFIRSTJavaOpMode extends LinearOpMode {
    // motor directions
    /** Directions that a given motor will turn in (swapping forward to reverse will swap the direction) */
    private DcMotorSimple.Direction forward = DcMotor.Direction.FORWARD;
    private DcMotorSimple.Direction reverse = DcMotor.Direction.REVERSE;
    // drive stuff
    /** The four motors in the drivetrain -- l/r for left/right then f/r for front/rear then d for drive */
    private DcMotor lfd;
    private DcMotor lrd;
    private DcMotor rfd;
    private DcMotor rrd;
    // lift stuff
    /** The four motors that control the lift kit. Same naming scheme as the drive motors */
    private DcMotor lfl;
    private DcMotor lrl;
    private DcMotor rfl;
    private DcMotor rrl;
    /** The positions the lift motors were in when the OpMode was initialized */
    private int lflStart;
    private int lrlStart;
    private int rflStart;
    private int rrlStart;
    /** The displacement of the lift motors since they were initialized */
    private int lflDistance;
    private int lrlDistance;
    private int rflDistance;
    private int rrlDistance;
    /** The maximum power to send to the lift motors*/
    private double maxLiftPower = 1.0;
    /** The distance from the target position at which to send the maximum power to the lift motors */
    private int maxPowerDistance = 40;
    /** The desired displacement for all of the lift kit motors */
    private double liftTarget = 0.0;
    /** I'm...honestly not totally sure what this was. I know we were trying to have the program automatically open and close the claws.
     * This does something related to that. */
    private double maxTargetSinceUp = liftTarget;
    // plane stuff
    /** The servo that controls the paper airplane's launch. */
    private Servo planeServo;
    /** The position for planeServo to be at before the plane is launched */
    private double planeStart = 0.5;
    /** The difference between the planeServo's positions before and after it is launched */
    private double planeDiff = -0.5;
    // claw stuff
    /** The servo that controls the claw on the left side */
    private Servo lClaw;
    /** The servo that controls the claw on the right side */
    private Servo rClaw;
    /** The position of lClaw when the left claw is open */
    private static double leftOpenPos = 0.703125;
    /** The difference between the open and closed positions of lClaw */
    private static double leftDiff = (double) -3/8;
    /** The position of rClaw when the right claw is open */
    private static double rightOpenPos = 0.3515625;
    /** The difference between the open and closed positions of rClaw */
    private static double rightDiff = (double) 3/8;
    // drum stuff
    /** The motor that spins the drum */
    private DcMotor drumMotor;
    /** The power to send to the drum motor when the drum is spinning */
    private double drumPower = -0.8;
    /** The function that will be called when we select this OpMode and hit "init".
     * Takes driver input and controls the wheels, lift kit, claws, and drum. */
    @Override
    public void runOpMode() {
        // Initialize drive motors
        lfd = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        lrd = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rfd = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rrd = hardwareMap.get(DcMotor.class, "rightRearDrive");

        // Set directions of drive motors
        lfd.setDirection(reverse);
        lrd.setDirection(forward);
        rfd.setDirection(forward);
        rrd.setDirection(forward);

        // Initialize lift motors
        lfl = hardwareMap.get(DcMotor.class, "leftFrontLift");
        lrl = hardwareMap.get(DcMotor.class, "leftRearLift");
        rfl = hardwareMap.get(DcMotor.class, "rightFrontLift");
        rrl = hardwareMap.get(DcMotor.class, "rightRearLift");

        // Set directions of lift motors
        lfl.setDirection(reverse);
        lrl.setDirection(forward);
        rfl.setDirection(reverse);
        rrl.setDirection(reverse);

        // Find and store starting positions for lift motors
        lflStart = lfl.getCurrentPosition();
        lrlStart = lrl.getCurrentPosition();
        rflStart = rfl.getCurrentPosition();
        rrlStart = rrl.getCurrentPosition();

        // Sets initial displacement of lift motors to 0
        lflDistance = 0;
        lrlDistance = 0;
        rflDistance = 0;
        rrlDistance = 0;

        // Initialize plane servo and sets it to the starting position (note that this does move a servo before the driver hits start,
        // which we should probably avoid in the future.
        planeServo = hardwareMap.get(Servo.class, "planeServo");
        planeServo.setPosition(planeStart);

        // Initialize claw servos
        lClaw = hardwareMap.get(Servo.class, "leftClaw");
        rClaw = hardwareMap.get(Servo.class, "rightClaw");

        // Initialize drum motor
        drumMotor = hardwareMap.get(DcMotor.class, "drumMotor");

        // True if the left and right bumpers are being held down, false otherwise
        boolean leftPressed = false;
        boolean rightPressed = false;
        // True if the left and right claws should be closed, false otherwise
        boolean leftClosed = false;
        boolean rightClosed = false;
        // True if the dpad down and up buttons are currently being held, false otherwise
        boolean downPressed = false;
        boolean upPressed = false;
        // Controls whether or not we are allowing the code to move the claw without direct driver input
        boolean doSmartClaw = true;

        // The amount of time that has elapsed since the (rough) start of the program
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        // What runtime.milliseconds() was the last time through the main while loop
        int lastMillisecond = (int) runtime.milliseconds();

        // Tells the driver that the robot is ready
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("leftRearPosition", lrd.getCurrentPosition());
            // The positions of the joysticks (swapped around for less confusion)
            double leftX = gamepad1.left_stick_x * -1.0;
            double leftY = gamepad1.left_stick_y * -1.0;
            double rightY = gamepad1.right_stick_y * -1.0;
            telemetry.addData("leftX", "" + leftX);
            telemetry.addData("leftY", "" + leftY);
            telemetry.addData("rightY", "" + rightY);

            // drive stuff
            // The positions of the left and right triggers
            double rDepth = gamepad1.right_trigger;
            double lDepth = gamepad1.left_trigger;
            // Moves the drivetrain based on the left joystick and the triggers
            translate(leftX, leftY);
            rotate(lDepth, rDepth);
            addDriveTelemetry();

            // TODO: why is this HERE?
            if (doSmartClaw) {
                if (liftTarget < maxTargetSinceUp - 250.0) { // TODO: magic number
                    leftClosed = true;
                    rightClosed = true;
                }
            }

            // claw stuff
            // Toggles the left claw when the left bumper is clicked
            if (gamepad1.left_bumper && !leftPressed) {
                leftClosed = !leftClosed;
                if (doSmartClaw)
                    maxTargetSinceUp = liftTarget;
                leftPressed = true;
            } else if (!gamepad1.left_bumper) {
                leftPressed = false;
            }
            setClaw(true, leftClosed);
            // Toggles the right claw when the right bumper is clicked
            if (gamepad1.right_bumper && !rightPressed) {
                rightClosed = !rightClosed;
                if (doSmartClaw)
                    maxTargetSinceUp = liftTarget;
                rightPressed = true;
            } else if (!gamepad1.right_bumper){
                rightPressed = false;
            }
            setClaw(false, rightClosed);

            addClawTelemetry();

            // plane stuff
            if (gamepad1.b)
                launchPlane();

            // drum stuff
            if (gamepad1.y)
                drumOn();
            else
                drumOff();

            // lift stuff
            // TODO: group all of the lift stuff in the same place (although we're probably going to just start over with a new class)
            int thisMillisecond = (int) runtime.milliseconds();
            // True if the runtime is greater by 1 millisecond or more than it was the last time, false otherwise
            boolean millisecondsChanged = lastMillisecond < thisMillisecond;
            // Moves the lift kit's target based on the right stick's y position
            if (millisecondsChanged) {
                liftTarget += gamepad1.right_stick_y * -3.0; // TODO: Ew magic number. Why would you do this, Past Zac?
                if (doSmartClaw) {
                    if (gamepad1.right_stick_y < 0) {
                        maxTargetSinceUp = liftTarget;
                    }
                }
                lastMillisecond = thisMillisecond; // TODO: if more than one millisecond passes between runs, the lift kit will slow down
            }

            telemetry.addData("millisecondsChanged", millisecondsChanged);
            telemetry.addData("lm", lastMillisecond);
            telemetry.addData("tm", thisMillisecond);


            // TODO: this probably should have been in a separate class
            if (gamepad1.dpad_up && !upPressed) {
                upPressed = true;
                maxLiftPower += 0.05; // TODO: magic number
            } else if (!gamepad1.dpad_up) {
                upPressed = false;
            }
            if (gamepad1.dpad_down && !downPressed) {
                downPressed = true;
                maxLiftPower -= 0.05; // TODO: magic number
            } else if (!gamepad1.dpad_down) {
                downPressed = false;
            }

            lift();

            addLiftTelemetry();
            telemetry.addData("Milliseconds", runtime.milliseconds());
            telemetry.addData("Up", gamepad1.dpad_up);
            telemetry.addData("down", gamepad1.dpad_down);
            // Prints all data in telemetry to the screen
            telemetry.update();
        }

    }
    //TODO: add parameter and return documentation to all methods
    /** Adds the power and position data from each drive motor to the telemetry. */
    public void addDriveTelemetry () {
        telemetry.addData("lfd power", lfd.getPower() + ", pos: " + lfd.getCurrentPosition());
        telemetry.addData("lrd power", lrd.getPower() + ", pos: " + lrd.getCurrentPosition());
        telemetry.addData("rfd power", lfd.getPower() + ", pos: " + rfd.getCurrentPosition());
        telemetry.addData("rrd power", rrd.getPower() + ", pos: " + rrd.getCurrentPosition());
    }
    /** Adds the power and position data from each lift motor, as well as the variables related to the lift kit, to the telemetry. */
    public void addLiftTelemetry () {
        telemetry.addData("lfl power", lfl.getPower() + ", dist: " + lflDistance);
        telemetry.addData("lrl power", lrl.getPower() + ", dist: " + lrlDistance);
        telemetry.addData("rfl power", rfl.getPower() + ", dist: " + rflDistance);
        telemetry.addData("rrl power", rrl.getPower() + ", dist: " + rrlDistance);
        telemetry.addData("liftTarget", liftTarget);
        telemetry.addData("maxLiftPower", maxLiftPower);
        telemetry.addData("maxTargetSinceUp", maxTargetSinceUp);
    }
    /** Doesn't work, but intended to add the current position of each claw to the telemetry */
    public void addClawTelemetry () {
        telemetry.addData("lClawPos", lClaw.getPosition()); //TODO: ew servos don't have feedback
        telemetry.addData("rClawPos", rClaw.getPosition());
    }
    /** Powers a given drive train in a translational direction.
     * ie, some combination of forward, back, left, and right, but no rotation. */
    public static void translate(double xVal, double yVal, DcMotor lfd, DcMotor lrd, DcMotor rfd, DcMotor rrd) {
        double totalPower = Math.hypot(xVal, yVal);
        if (totalPower > 1) totalPower = 1;
        //if (totalPower < -1) totalPower = -1;
        if (xVal == 0.0 && yVal == 0.0) {
            lfd.setPower(0.0);
            lrd.setPower(0.0);
            rfd.setPower(0.0);
            rrd.setPower(0.0);
        } else if (xVal >= 0.0 && yVal >= 0.0) {
            lrd.setPower(totalPower);
            rfd.setPower(totalPower);
            double oppPower = yVal - xVal;
            lfd.setPower(oppPower);
            rrd.setPower(oppPower);
        } else if (xVal <= 0.0 && yVal >= 0.0) {
            lfd.setPower(totalPower);
            rrd.setPower(totalPower);
            double oppPower = yVal + xVal;
            lrd.setPower(oppPower);
            rfd.setPower(oppPower);
        } else if (xVal <= 0.0 && yVal <= 0.0) {
            lrd.setPower(-1 * totalPower);
            rfd.setPower(-1 * totalPower);
            double oppPower = yVal - xVal;
            lfd.setPower(oppPower);
            rrd.setPower(oppPower);
        } else {
            lfd.setPower(-1 * totalPower);
            rrd.setPower(-1 * totalPower);
            double oppPower = yVal + xVal;
            lrd.setPower(oppPower);
            rfd.setPower(oppPower);
        }
    }
    /** Powers the drive train in a translational direction.
     * ie, some combination of forward, back, left, and right, but no rotation.*/
    public void translate(double xVal, double yVal) {
        translate(xVal, yVal, lfd, lrd, rfd, rrd);
    }
    /** Powers the drive train to rotate the robot. */
    public void rotate(double lDepth, double rDepth) {
        if (rDepth > 0) {
            lfd.setPower(lfd.getPower() + rDepth);
            lrd.setPower(lrd.getPower() + rDepth);
            rfd.setPower(rfd.getPower() + (-1 * rDepth));
            rrd.setPower(rrd.getPower() + (-1 * rDepth));
        } else if (lDepth > 0){
            lfd.setPower(lfd.getPower() + (-1 * lDepth));
            lrd.setPower(lrd.getPower() + (-1 * lDepth));
            rfd.setPower(rfd.getPower() + lDepth);
            rrd.setPower(rrd.getPower() + lDepth);
        }
    }/*
    public void lift() {
        updateLiftDistances();

        updateLiftMotorPower(lfl, lflDistance);
        updateLiftMotorPower(lrl, lrlDistance);
        updateLiftMotorPower(rfl, rflDistance);
        updateLiftMotorPower(rrl, rrlDistance);
    }*/
    /** Moves the lift kit motors towards their target.
     * (This is probably a bad way of explaining it. It involves proportional control.)*/
    public void lift() {
        updateLiftDistances();

        updateLiftMotorPower(lfl, lflDistance);
        updateLiftMotorPower(lrl, lrlDistance);
        updateLiftMotorPower(rfl, rflDistance);
        updateLiftMotorPower(rrl, rrlDistance);
    }
    /** Calculates and stores the displacements each lift kit motor has travelled since the start*/
    public void updateLiftDistances() {
        // TODO: make this automatically sync with the direction of the motor (basically ew magic number)
        lflDistance = lfl.getCurrentPosition() - lflStart;
        lrlDistance = (lrl.getCurrentPosition() - lrlStart) * -1;
        rflDistance = (rfl.getCurrentPosition() - rflStart) * -1;
        rrlDistance = (rrl.getCurrentPosition() - rrlStart) * -1;
    }
    /** Returns the average displacement of the lift kit motors
     * Unused, probably because it doesn't work. */
    public int averageLiftDistance() {
        return (lflDistance + lrlDistance + rflDistance + rrlDistance)/4;
    }
    /** Sends power to the given motor based on the given distance it is from its target */
    public void updateLiftMotorPower(DcMotor motor, int distance) {
        int error = distance - (int) liftTarget;
        if (error > maxPowerDistance)
            motor.setPower(maxLiftPower);
        else if (error < (-1.0 * maxPowerDistance))
            motor.setPower(-1.0 * maxLiftPower);
        else
            motor.setPower(((double) error/maxPowerDistance) * maxLiftPower);
    }
    /** Launches the paper airplane. */
    public void launchPlane () {
        planeServo.setPosition(planeStart + planeDiff);
    }
    /** Sets the position of the given claw based on which claw it is and whether it should be open or closed. */
    public static void setClaw (boolean settingLeft, boolean closed, Servo claw) {
        if (settingLeft) {
            if (closed) {
                claw.setPosition(leftOpenPos + leftDiff);
            } else {
                claw.setPosition(leftOpenPos);
            }
        } else {
            if (closed) {
                claw.setPosition(rightOpenPos + rightDiff);
            } else {
                claw.setPosition(rightOpenPos);
            }
        }
    }
    /** Sets the position of the specified claw based on which claw it is and whether it should be open or closed.*/
    public void setClaw (boolean settingLeft, boolean closed) {
        if (settingLeft)
            setClaw(true, closed, lClaw);
        else
            setClaw(false, closed, rClaw);
    }
    /** Spins the drum */
    public void drumOn () {
        drumMotor.setPower(drumPower);
    }
    /** Stops spinning the drum (Camina Drummer is now sad) */
    public void drumOff () {
        drumMotor.setPower(0.0);
    }
}

