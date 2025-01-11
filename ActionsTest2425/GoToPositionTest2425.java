package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

import java.util.ArrayList;

@TeleOp(name = "GoToPositionTest2425", group = "Linear OpMode")
public class GoToPositionTest2425 extends LinearOpMode  {
    DriveTrain2425 driveTrain;
    PositionFinder2425 positionFinder;
    double tolerance;
    @Override
    public void runOpMode () {
        telemetry.update();
        tolerance = 5;
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
        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(-60, 0, 0), tolerance, positionFinder));
        actionList.add(new GoToPosition2425(driveTrain, new Position(60, 0, 0), tolerance, positionFinder));

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
            telemetry.addData("position", positionFinder.getPosition());


            telemetry.update();
        }
    }

}
