package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "ArmActionTest2425", group = "Linear OpMode")
public class ArmActionTest2425 extends LinearOpMode {


    @Override
    public void runOpMode () {
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {


            telemetry.update();
        }
    }

}
