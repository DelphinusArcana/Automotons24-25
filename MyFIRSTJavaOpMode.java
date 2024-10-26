package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// Testing git with this comment
@TeleOp(name="Template: 2024 Brie 4", group="Linear Opmode")
public class MyFIRSTJavaOpMode extends LinearOpMode {
    // motor directions
    private DcMotorSimple.Direction forward = DcMotor.Direction.FORWARD;
    private DcMotorSimple.Direction reverse = DcMotor.Direction.REVERSE;
    // drive stuff
    private DcMotor lfd;
    private DcMotor lrd;
    private DcMotor rfd;
    private DcMotor rrd;
    // lift stuff
    /*
    private DcMotor lfl;
    private DcMotor lrl;
    private DcMotor rfl;
    private DcMotor rrl;
    private int lflStart;
    private int lrlStart;
    private int rflStart;
    private int rrlStart;
    private int lflDistance;
    private int lrlDistance;
    private int rflDistance;
    private int rrlDistance;
    private double maxLiftPower = 1.0;
    private int maxPowerDistance = 40;
    private double liftTarget = 0.0;
    private double maxTargetSinceUp = liftTarget;
    // plane stuff
    private Servo planeServo;
    private double planeStart = 0.5;
    private double planeDiff = -0.5;*/
    // claw stuff
    private Servo lClaw;
    private Servo rClaw;
    private static double leftOpenPos = 0.703125;
    private static double leftDiff = (double) -3/8;
    private static double rightOpenPos = 0.3515625;
    private static double rightDiff = (double) 3/8;
    // drum stuff
    /*private DcMotor drumMotor;
    private double drumPower = -0.8;*/
    @Override
    public void runOpMode() {
        lfd = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        lrd = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rfd = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rrd = hardwareMap.get(DcMotor.class, "rightRearDrive");

        lfd.setDirection(reverse);
        lrd.setDirection(forward);
        rfd.setDirection(forward);
        rrd.setDirection(forward);
/*
        lfl = hardwareMap.get(DcMotor.class, "leftFrontLift");
        lrl = hardwareMap.get(DcMotor.class, "leftRearLift");
        rfl = hardwareMap.get(DcMotor.class, "rightFrontLift");
        rrl = hardwareMap.get(DcMotor.class, "rightRearLift");

        lfl.setDirection(reverse);
        lrl.setDirection(forward);
        rfl.setDirection(reverse);
        rrl.setDirection(reverse);

        lflStart = lfl.getCurrentPosition();
        lrlStart = lrl.getCurrentPosition();
        rflStart = rfl.getCurrentPosition();
        rrlStart = rrl.getCurrentPosition();

        lflDistance = 0;
        lrlDistance = 0;
        rflDistance = 0;
        rrlDistance = 0;

        planeServo = hardwareMap.get(Servo.class, "planeServo");
        planeServo.setPosition(planeStart);

        lClaw = hardwareMap.get(Servo.class, "leftClaw");
        rClaw = hardwareMap.get(Servo.class, "rightClaw");

        drumMotor = hardwareMap.get(DcMotor.class, "drumMotor");
*/
        boolean leftPressed = false;
        boolean rightPressed = false;
        boolean leftClosed = false;
        boolean rightClosed = false;
        boolean downPressed = false;
        boolean upPressed = false;
        boolean doSmartClaw = true;

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        int lastMillisecond = (int) runtime.milliseconds();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("leftRearPosition", lrd.getCurrentPosition());
            double leftX = gamepad1.left_stick_x * -1.0;
            double leftY = gamepad1.left_stick_y * -1.0;
            double rightY = gamepad1.right_stick_y * -1.0;
            telemetry.addData("leftX", "" + leftX);
            telemetry.addData("leftY", "" + leftY);
            telemetry.addData("rightY", "" + rightY);
            // drive stuff
            double rDepth = gamepad1.right_trigger;
            double lDepth = gamepad1.left_trigger;
            translate(leftX, leftY);
            rotate(lDepth, rDepth);
            addDriveTelemetry();
            /*
            if (doSmartClaw) {
                if (liftTarget < maxTargetSinceUp - 250.0) {
                    leftClosed = true;
                    rightClosed = true;
                }
            }
            // claw stuff
            if (gamepad1.left_bumper && !leftPressed) {
                leftClosed = !leftClosed;
                if (doSmartClaw)
                    maxTargetSinceUp = liftTarget;
                leftPressed = true;
            } else if (!gamepad1.left_bumper) {
                leftPressed = false;
            }
            setClaw(true, leftClosed);
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
            int thisMillisecond = (int) runtime.milliseconds();
            boolean millisecondsChanged = lastMillisecond < thisMillisecond;
            if (millisecondsChanged) {
                liftTarget += gamepad1.right_stick_y * -3.0;
                if (doSmartClaw) {
                    if (gamepad1.right_stick_y < 0) {
                        maxTargetSinceUp = liftTarget;
                    }
                }
                lastMillisecond = thisMillisecond;
            }
            telemetry.addData("millisecondsChanged", millisecondsChanged);
            telemetry.addData("lm", lastMillisecond);
            telemetry.addData("tm", thisMillisecond);
            if (gamepad1.dpad_up && !upPressed) {
                upPressed = true;
                maxLiftPower += 0.05;
            } else if (!gamepad1.dpad_up) {
                upPressed = false;
            }
            if (gamepad1.dpad_down && !downPressed) {
                downPressed = true;
                maxLiftPower -= 0.05;
            } else if (!gamepad1.dpad_down) {
                downPressed = false;
            }
            lift();

            addLiftTelemetry();*/
            telemetry.addData("Milliseconds", runtime.milliseconds());
            telemetry.addData("Up", gamepad1.dpad_up);
            telemetry.addData("down", gamepad1.dpad_down);
            telemetry.update();
        }

    }
    public void addDriveTelemetry () {
        telemetry.addData("lfd power", lfd.getPower() + ", pos: " + lfd.getCurrentPosition());
        telemetry.addData("lrd power", lrd.getPower() + ", pos: " + lrd.getCurrentPosition());
        telemetry.addData("rfd power", lfd.getPower() + ", pos: " + rfd.getCurrentPosition());
        telemetry.addData("rrd power", rrd.getPower() + ", pos: " + rrd.getCurrentPosition());
    }/*
    public void addLiftTelemetry () {
        telemetry.addData("lfl power", lfl.getPower() + ", dist: " + lflDistance);
        telemetry.addData("lrl power", lrl.getPower() + ", dist: " + lrlDistance);
        telemetry.addData("rfl power", rfl.getPower() + ", dist: " + rflDistance);
        telemetry.addData("rrl power", rrl.getPower() + ", dist: " + rrlDistance);
        telemetry.addData("liftTarget", liftTarget);
        telemetry.addData("maxLiftPower", maxLiftPower);
        telemetry.addData("maxTargetSinceUp", maxTargetSinceUp);
    }
    public void addClawTelemetry () {
        telemetry.addData("lClawPos", lClaw.getPosition());
        telemetry.addData("rClawPos", rClaw.getPosition());
    }*/
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
    public void translate(double xVal, double yVal) {
        translate(xVal, yVal, lfd, lrd, rfd, rrd);
    }
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
    }
    public void lift() {
        updateLiftDistances();

        updateLiftMotorPower(lfl, lflDistance);
        updateLiftMotorPower(lrl, lrlDistance);
        updateLiftMotorPower(rfl, rflDistance);
        updateLiftMotorPower(rrl, rrlDistance);
    }
    public void updateLiftDistances() {
        lflDistance = lfl.getCurrentPosition() - lflStart;
        lrlDistance = (lrl.getCurrentPosition() - lrlStart) * -1;
        rflDistance = (rfl.getCurrentPosition() - rflStart) * -1;
        rrlDistance = (rrl.getCurrentPosition() - rrlStart) * -1;
    }
    public int averageLiftDistance() {
        return (lflDistance + lrlDistance + rflDistance + rrlDistance)/4;
    }
    public void updateLiftMotorPower(DcMotor motor, int distance) {
        int error = distance - (int) liftTarget;
        if (error > maxPowerDistance)
            motor.setPower(maxLiftPower);
        else if (error < (-1.0 * maxPowerDistance))
            motor.setPower(-1.0 * maxLiftPower);
        else
            motor.setPower(((double) error/maxPowerDistance) * maxLiftPower);
    }
    public void launchPlane () {
        planeServo.setPosition(planeStart + planeDiff);
    }*/
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
    }/*
    public void setClaw (boolean settingLeft, boolean closed) {
        if (settingLeft)
            setClaw(true, closed, lClaw);
        else
            setClaw(false, closed, rClaw);
    }
    public void drumOn () {
        drumMotor.setPower(drumPower);
    }
    public void drumOff () {
        drumMotor.setPower(0.0);
    }*/
}

