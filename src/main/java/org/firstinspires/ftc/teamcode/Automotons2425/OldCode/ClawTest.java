package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.OldCode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name="ClawTest1", group="Linear Opmode")
public class ClawTest extends LinearOpMode {
    private Servo lClaw;
    private Servo rClaw;
    private double leftOpenPos = 0.703125;
    private double leftDiff = (double) -3/8;
    private double rightOpenPos = 0.3515625;
    private double rightDiff = (double) 3/8;
    @Override
    public void runOpMode () {
        lClaw = hardwareMap.get(Servo.class, "leftClaw");
        rClaw = hardwareMap.get(Servo.class, "rightClaw");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        boolean upPressed = false;
        boolean downPressed = false;
        boolean xPressed = false;
        boolean yPressed = false;
        boolean leftPressed = false;
        boolean rightPressed = false;
        boolean leftClosed = false;
        boolean rightClosed = false;
        boolean changingLeft = true;
        boolean changingDiff = true;


        while (opModeIsActive()) {
            telemetry.addData("leftOpenPos", leftOpenPos);
            telemetry.addData("leftDiff", leftDiff);
            telemetry.addData("rightOpenPos", rightOpenPos);
            telemetry.addData("rightDiff", rightDiff);
            telemetry.addData("changingLeft", changingLeft);
            telemetry.addData("changingDiff", changingDiff);
            telemetry.addData("leftPos", lClaw.getPosition());
            telemetry.addData("rightPos", rClaw.getPosition());
            // claws toggle
            if (gamepad1.left_bumper && !leftPressed) {
                leftClosed = !leftClosed;
                leftPressed = true;
            } else if (!gamepad1.left_bumper){
                leftPressed = false;
            }
            setClaw(true, leftClosed);
            if (gamepad1.right_bumper && !rightPressed) {
                rightClosed = !rightClosed;
                rightPressed = true;
            } else if (!gamepad1.right_bumper){
                rightPressed = false;
            }
            setClaw(false, rightClosed);

            // find angles
            if (gamepad1.x && !xPressed) {
                changingLeft = !changingLeft;
                xPressed = true;
            } else if (!gamepad1.x){
                xPressed = false;
            }
            if (gamepad1.y && !yPressed) {
                changingDiff = !changingDiff;
                yPressed = true;
            } else if (!gamepad1.y){
                yPressed = false;
            }
            if (gamepad1.dpad_up && !upPressed) {
                if (changingLeft && changingDiff)
                    leftDiff += (double) 1/128;
                else if (changingLeft)
                    leftOpenPos += (double) 1/128;
                else if (changingDiff)
                    rightDiff += (double) 1/128;
                else
                    rightOpenPos += (double) 1/128;
                upPressed = true;
            } else if (!gamepad1.dpad_up){
                upPressed = false;
            }
            if (gamepad1.dpad_down && !downPressed) {
                if (changingLeft && changingDiff)
                    leftDiff -= (double) 1/128;
                else if (changingLeft)
                    leftOpenPos -= (double) 1/128;
                else if (changingDiff)
                    rightDiff -= (double) 1/128;
                else
                    rightOpenPos -= (double) 1/128;
                downPressed = true;
            } else if (!gamepad1.dpad_down){
                downPressed = false;
            }
            telemetry.update();
        }
    }
    private void setClaw (boolean settingLeft, boolean closed) {
        if (settingLeft) {
            if (closed) {
                lClaw.setPosition(leftOpenPos + leftDiff);

            } else {
                lClaw.setPosition(leftOpenPos);
            }
        } else {
            if (closed) {
                rClaw.setPosition(rightOpenPos + rightDiff);
            } else {
                rClaw.setPosition(rightOpenPos);
            }
        }
    }

}

