package org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

@TeleOp(name = "DriveTest2425", group = "Linear Opmode")
public class DriveTest2425 extends LinearOpMode {
    private DriveTrain2425 driveTrain;
    private Position drivePosition;
    private double[] wheelPosition;
    private double minTranslatePower; //minimum move speed
    private int moveSpeed; //coefficient that changes driveTrain translation values
    private PositionFinder2425 positionFinder;

    @Override
    public void runOpMode () {
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
        }, //TODO: make something that can find/update the directions
                new boolean[] {true,false,true,false}
        );
        drivePosition =  new Position(0,0,0);
        positionFinder = new PositionFinder2425(driveTrain, drivePosition);

        wheelPosition = driveTrain.getWheelPosition();
        moveSpeed = 1;
        minTranslatePower = 0.25;
        telemetry.addData("Status","Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,drive train control
            double leftX = gamepad1.left_stick_x;
            double leftY = gamepad1.left_stick_y;
            driveTrain.translate(calcTranslatePower(leftX),calcTranslatePower(leftY));

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            driveTrain.rotate(calcRotatePower(leftTrigger), calcRotatePower(rightTrigger));

            wheelPosition = driveTrain.getWheelPosition();
            positionFinder.updatePosition();

            telemetry.addData("wheel position 1", wheelPosition[0]);
            telemetry.addData("wheel position 2", wheelPosition[1]);
            telemetry.addData("wheel position 3", wheelPosition[2]);
            telemetry.addData("wheel position 4", wheelPosition[3]);
            
            telemetry.addData("Drive Position aPos", positionFinder.getPosition().aPos);
            telemetry.addData("Drive Position bPos", positionFinder.getPosition().bPos);
            telemetry.addData("Drive Position facing direction", positionFinder.getPosition().facingDirection);

            telemetry.update();
        }
    }
    public double calcTranslatePower (double stickPos) {
        return (stickPos*(1-minTranslatePower)+Math.signum(stickPos)*minTranslatePower)*moveSpeed;
    }
    public double calcRotatePower (double triggerPos) {
        return triggerPos;
    }
}
