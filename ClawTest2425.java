package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="ClawTest2425", group="Linear Opmode")
public class ClawTest2425 extends LinearOpMode {
    private Servo claw;
    private double openPos = 0.703125;
    private double diff = (double) -3/8;
    @Override
    public void runOpMode () {
        claw = hardwareMap.get(Servo.class, "claw");
        
        boolean upPressed = false;
        boolean downPressed = false;
        boolean yPressed = false;
        boolean bumpPressed = false;
        boolean clawClosed = false;
        boolean changingDiff = true;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("openPos", openPos);
            telemetry.addData("diff", diff);
            telemetry.addData("changingDiff", changingDiff);
            telemetry.addData("pos", claw.getPosition());

            // claws toggle
            if (gamepad1.right_bumper && !bumpPressed) {
                clawClosed = !clawClosed;
                bumpPressed = true;
            } else if (!gamepad1.right_bumper){
                bumpPressed = false;
            }
            setClaw(clawClosed);

            // find angles
            if (gamepad1.y && !yPressed) {
                changingDiff = !changingDiff;
                yPressed = true;
            } else if (!gamepad1.y){
                yPressed = false;
            }
            if (gamepad1.dpad_up && !upPressed) {
                if (changingDiff)
                    diff += (double) 1/128;
                else
                    openPos += (double) 1/128;
                upPressed = true;
            } else if (!gamepad1.dpad_up){
                upPressed = false;
            }
            if (gamepad1.dpad_down && !downPressed) {
                if (changingDiff)
                    diff -= (double) 1/128;
                else
                    openPos -= (double) 1/128;
                downPressed = true;
            } else if (!gamepad1.dpad_down){
                downPressed = false;
            }
            telemetry.update();
        }
    }
    private void setClaw (boolean closed) {
        if (closed) {
            claw.setPosition(openPos + diff);
        } else {
            claw.setPosition(openPos);
        }
    }
}

