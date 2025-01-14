package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;


import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

import java.util.ArrayList;

@TeleOp(name = "GoToPositionTest2425", group = "Linear OpMode")
public class GoToPositionTest2425 extends LinearOpMode  {
    private DriveTrain2425 driveTrain;
    private PositionFinder2425 positionFinder;
    private ButtonWatcher2425 dPadUp;
    private ButtonWatcher2425 dPadDown;
    private double tolerance;
    @Override
    public void runOpMode () {
        telemetry.update();
        tolerance = 5;
        dPadUp = new ButtonWatcher2425();
        dPadDown = new ButtonWatcher2425();

        ArrayList<GoToPosition2425> actionList = new ArrayList<GoToPosition2425>();

        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));


        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, -60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, -60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, -60, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(0, 60, 0), tolerance, positionFinder));

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            actionList.get(0).doAction();

            if (actionList.get(0).isComplete()) {
                actionList.remove(0);
                telemetry.speak("---------------------");
                telemetry.speak("---------------------");
                telemetry.speak("Starting next action");
                telemetry.speak("---------------------");
                telemetry.speak("---------------------");

            }
            if (dPadUp.pressed(gamepad1.dpad_up)){
                tolerance+=0.2;
            }
            if (dPadDown.pressed(gamepad1.dpad_down)){
                tolerance-=0.2;
            }
            telemetry.addData("tolerance", tolerance);
            telemetry.addData("position", positionFinder.getPosition());
            telemetry.update();
        }
    }

}
