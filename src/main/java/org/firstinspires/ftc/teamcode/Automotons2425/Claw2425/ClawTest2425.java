package org.firstinspires.ftc.teamcode.Automotons2425.Claw2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name="ClawTest2425", group="Linear Opmode")
public class ClawTest2425 extends LinearOpMode {
    private double openPos = 0.703125;
    private double diff = (double) -3/8;
    private Claw2425 claw;
    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;


    @Override
    public void runOpMode () {
        claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));
        
        boolean upPressed = false;
        boolean downPressed = false;
        boolean yPressed = false;
        boolean rBumpPressed = false;
        boolean clawClosed = false;
        boolean changingDiff = true;
        dpadUp2 = new ButtonWatcher2425();
        dpadDown2 = new ButtonWatcher2425();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("openPos", openPos);
            telemetry.addData("diff", diff);
            telemetry.addData("changingDiff", changingDiff);

            // claws toggle
            if (gamepad1.right_bumper && !rBumpPressed) {
                claw.toggleClaw();
                rBumpPressed = true;
            } else if (!gamepad1.right_bumper) {
                rBumpPressed = false;
            }

            //claw calibration
            if (dpadUp2.pressed(gamepad2.dpad_up)){
                claw.shiftPositions((double) -1 /32);
            }
            if (dpadDown2.pressed(gamepad2.dpad_down)){
                claw.shiftPositions((double) 1 /32);
            }


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
}

