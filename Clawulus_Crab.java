package org.firstinspires.ftc.Automotons;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="Clawulus", group="Linear Opmode")
public class Clawulus_Crab extends LinearOpMode {
    private Servo claw;
    private final double baseAngle = 0.5;
    private double openDiff = -0.5;
    private double closeDiff = 0.5;
    private double speed = 0.2;
    private double angle;
    private double theGhostOfRuntimePast;
    private double timeSpeed;
    @Override
    public void runOpMode () {
        claw = hardwareMap.get(Servo.class, "claw");

        telemetry.addData("Status", "Initialized");
        telemetry.addData("angle", angle);
        telemetry.addData("OpenDiff", openDiff);
        telemetry.addData("closeDiff", closeDiff);
        telemetry.addData("speed", speed);
        telemetry.update();
        claw.setPosition(baseAngle);
        ElapsedTime runtime=new ElapsedTime();
        theGhostOfRuntimePast=runtime.milliseconds();
        runtime.reset();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeIsActive()) {
            timeSpeed=(runtime.milliseconds())/100;
            runtime.reset();
            if (gamepad1.y) {
                speed=Math.min(1,speed+0.001*timeSpeed);
            }
            if (gamepad1.a) {
                speed+=Math.max(0,speed-0.001*timeSpeed);
            }
            if (gamepad1.dpad_left) {
                openDiff+=0.001*timeSpeed;
            }
            if (gamepad1.dpad_right) {
                openDiff-=0.001*timeSpeed;
            }
            if (gamepad1.x) {
                closeDiff-=0.001*timeSpeed;
            }
            if (gamepad1.b) {
                closeDiff+=0.001*timeSpeed;
            }
            if (gamepad1.left_trigger>0) {
                angle=Math.max(baseAngle+openDiff,angle+-speed*timeSpeed);
            }
            if (gamepad1.right_trigger>0) {
                angle=Math.min(baseAngle+closeDiff,angle+speed*timeSpeed);
            }
            claw.setPosition(angle);
            telemetry.addData("angle", angle);
            telemetry.addData("OpenDiff", openDiff);
            telemetry.addData("closeDiff", closeDiff);
            telemetry.addData("speed", speed);
            telemetry.update();
        }
    }
}