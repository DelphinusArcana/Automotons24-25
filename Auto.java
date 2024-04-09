
package org.firstinspires.ftc.Automotons;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="MyAutonomousOpMode")
public class Auto extends LinearOpMode {
    // motors
    private DcMotor lfd;
    private DcMotor lrd;
    private DcMotor rfd;
    private DcMotor rrd;
    // motor directions
    private DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    private DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;
    // claws
    private Servo lClaw;
    private Servo rClaw;
    // movement variables
    private int driveTarget = 2000;
    private double maxDrivePower = 0.8;
    private int maxPowerDistance = 500;
    private int lfdStart;
    private int lrdStart;
    private int rfdStart;
    private int rrdStart;
    private int lfdDistance;
    private int lrdDistance;
    private int rfdDistance;
    private int rrdDistance;
    // whether the pixel has been dropped yet
    private boolean dropPixel = false;

    @Override
    public void runOpMode() {
        lfd = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        lrd = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rfd = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rrd = hardwareMap.get(DcMotor.class, "rightRearDrive");

        lfd.setDirection(forward);
        lrd.setDirection(forward);
        rfd.setDirection(forward);
        rrd.setDirection(reverse);

        lfdStart = lfd.getCurrentPosition();
        lrdStart = lrd.getCurrentPosition();
        rfdStart = rfd.getCurrentPosition();
        rrdStart = rrd.getCurrentPosition();

        updateDriveDistances();

        rClaw = hardwareMap.get(Servo.class, "rightClaw");
        MyFIRSTJavaOpMode.setClaw(false, true, rClaw);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();


        while (opModeIsActive() && !dropPixel) {
            MyFIRSTJavaOpMode.setClaw(false, true, rClaw);

            updateDriveDistances();

            updateDriveMotorPower(lfd, lfdDistance);
            updateDriveMotorPower(lrd, lrdDistance);
            updateDriveMotorPower(rfd, rfdDistance);
            updateDriveMotorPower(rrd, rrdDistance);

            if (averageDriveDistance() >= driveTarget * 0.5)
                dropPixel = true;

            telemetry.addData("loop", "first");
            telemetry.addData("avgDistance", averageDriveDistance());
            addDriveTelemetry();
            telemetry.update();
        }
        while (opModeIsActive()) {
            MyFIRSTJavaOpMode.setClaw(false, false, rClaw);

            updateDriveDistances();

            updateDriveMotorPower(lfd, lfdDistance);
            updateDriveMotorPower(lrd, lrdDistance);
            updateDriveMotorPower(rfd, rfdDistance);
            updateDriveMotorPower(rrd, rrdDistance);

            telemetry.addData("loop", "second");
            telemetry.addData("avgDistance", averageDriveDistance());
            addDriveTelemetry();
            telemetry.update();
        }
    }
    public void updateDriveMotorPower(DcMotor motor, int distance) {
        int error = driveTarget - distance;
        if (error > maxPowerDistance)
            motor.setPower(maxDrivePower);
        else if (error < (-1.0 * maxPowerDistance))
            motor.setPower(-1.0 * maxDrivePower);
        else
            motor.setPower(((double) error / maxPowerDistance) * maxDrivePower);
    }
    public void translate(double xVal, double yVal) {
        MyFIRSTJavaOpMode.translate(xVal, yVal, lfd, lrd, rfd, rrd);
    }
    public void updateDriveDistances() {
        lfdDistance = lfd.getCurrentPosition() - lfdStart;
        lrdDistance = (lrd.getCurrentPosition() - lrdStart) * -1;
        rfdDistance = (rfd.getCurrentPosition() - rfdStart) * -1;
        rrdDistance = (rrd.getCurrentPosition() - rrdStart) * -1;
    }
    public int averageDriveDistance() {
        return (lfdDistance + lrdDistance + rfdDistance + rrdDistance) / 4;
    }
    public void addDriveTelemetry () {
        telemetry.addData("lfd power", lfd.getPower() + ", pos: " + lfdDistance);
        telemetry.addData("lrd power", lrd.getPower() + ", pos: " + lrdDistance);
        telemetry.addData("rfd power", lfd.getPower() + ", pos: " + rfdDistance);
        telemetry.addData("rrd power", rrd.getPower() + ", pos: " + rrdDistance);
    }
}
