package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.*;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.*;
@TeleOp(name = "SetLiftTest2425", group = "Linear OpMode")
public class SetLiftTest2425 extends LinearOpMode {
    Lift2425 liftKit;
    boolean running;
    double desiredPosition;
    double tolerance;
    //variable declaration - variable
    private int liftMaxHeight; //upperbound of liftkit position
    private int liftMinimumHeight; //lower bound of liftkit position- might be unneeded if equals 0
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private double liftSpeed;
    ButtonWatcher2425 dpadUp;
    ButtonWatcher2425 dpadDown;
    ButtonWatcher2425 leftBumper;
    SetLift2425 action;
    @Override
    public void runOpMode () {
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftLift"),
                hardwareMap.get(DcMotor.class, "rightLift")
        }, new boolean[] {false, true});
        running = false;
        desiredPosition = liftKit.getAverageHeight();
        tolerance = 10;
        action = new SetLift2425(liftKit,desiredPosition,tolerance);

        //TODO: find these values
        liftSpeed = 0.5;
        liftMaxHeight = 0;
        liftMinimumHeight = -3600;

        dpadUp = new ButtonWatcher2425();
        dpadDown = new ButtonWatcher2425();
        leftBumper = new ButtonWatcher2425();

        telemetry.addData("Status","Initialized");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            //get time since loop was last looped
            int currentMilli = (int) runtime.milliseconds();
            int timeCoef = currentMilli-saveElapsedMilli;
            saveElapsedMilli = currentMilli;

            //change lift kit target
            double rightY = gamepad1.right_stick_y;
            desiredPosition += rightY * liftSpeed * timeCoef;

            //Change desired height
            if (desiredPosition>liftMaxHeight) {
                desiredPosition = liftMaxHeight;
            } else if (desiredPosition<liftMinimumHeight){
                desiredPosition = liftMinimumHeight;
            }

            //Change whether we're running with left bumper
            if (leftBumper.pressed(gamepad1.left_bumper))
                running = !running;

            //Change tolerance by +/- 1 with dpad
            if (dpadUp.pressed(gamepad1.dpad_up))
                tolerance += 1;
            if (dpadDown.pressed(gamepad1.dpad_down))
                tolerance -= 1;

            if (running)
                action = new SetLift2425(liftKit,desiredPosition,tolerance);

            if (action.isComplete()) {
                telemetry.addData("Status","Done");
            } else if (running) {
                action.doAction();
                telemetry.addData("Status","Running");
            } else {
                telemetry.addData("Status","Editing");
            }
            telemetry.addData("desiredPosition",desiredPosition);
            telemetry.addData("tolerance",tolerance);
            telemetry.addData("lift height",liftKit.getAverageHeight());
            telemetry.addData("target height",liftKit.getTargetHeight());
            telemetry.addData("max height", liftMaxHeight);
            telemetry.addData("min height", liftMinimumHeight);
            telemetry.update();
        }
    }

}
