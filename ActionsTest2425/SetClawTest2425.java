package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;

import java.util.ArrayList;


@TeleOp(name = "SetClawTest2425", group = "Linear OpMode")
public class SetClawTest2425 extends LinearOpMode  {
    Claw2425 Claw;
    double saveElapsedMilli;
    @Override
    public void runOpMode () {
        ArrayList<SetClaw2425> actionList = new ArrayList<SetClaw2425>();

        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));
        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));
        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));

        Claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));
        ElapsedTime runtime = new ElapsedTime();
        saveElapsedMilli = runtime.milliseconds();


        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            actionList.get(0).doAction();

            if (runtime.milliseconds()-saveElapsedMilli == 1000){
                saveElapsedMilli = runtime.milliseconds();
                if (actionList.get(0).isComplete()) {
                   actionList.remove(0);
                }
            }


            telemetry.update();
        }
    }

}
