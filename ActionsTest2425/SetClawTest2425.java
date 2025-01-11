package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;

import java.util.ArrayList;


@Autonomous(name = "SetClawTest2425")
public class SetClawTest2425 extends LinearOpMode  {
    Claw2425 Claw;
    double saveElapsedMilli;
    boolean clawState;
    @Override
    public void runOpMode () {
        clawState = true;
        Claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));

        ArrayList<SetClaw2425> actionList = new ArrayList<SetClaw2425>();

        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));
        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));
        actionList.add(new SetClaw2425(Claw, true));
        actionList.add(new SetClaw2425(Claw, false));

        ElapsedTime runtime = new ElapsedTime();
        saveElapsedMilli = runtime.milliseconds();


        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            actionList.get(0).doAction();

            if (runtime.milliseconds()-saveElapsedMilli == 1000){
                telemetry.speak("1 second has passed");
                if (actionList.get(0).isComplete()) {
                    saveElapsedMilli = runtime.milliseconds();
                    actionList.remove(0);
                    telemetry.speak("Toggling claw");
                    clawState = !clawState;
                }
            }
            telemetry.addData("Claw action is",clawState);
            telemetry.update();
        }
    }

}
