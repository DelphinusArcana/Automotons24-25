package org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "DriveTest2425", group = "Linear Opmode")
public class DriveTest2425 extends LinearOpMode {
    private DriveTrain2425 driveTrain;
    private Position drivePosition;
    private double[] wheelPosition;
    private PositionFinder2425 positionFinder;
    private ButtonWatcher2425 leftBumper;

    @Override
    public void runOpMode () {
        driveTrain = DriveTrain2425.defaultDrive(hardwareMap);
        drivePosition =  new Position(0,0,0);
        positionFinder = new PositionFinder2425(driveTrain, drivePosition);
        leftBumper = new ButtonWatcher2425();
        boolean oppPowerCheck = true;

        wheelPosition = driveTrain.getWheelPosition();
        telemetry.addData("Status","Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            if (leftBumper.pressed(gamepad1.left_bumper))
                oppPowerCheck = !oppPowerCheck;
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,drive train control
            double leftX = gamepad1.left_stick_x;
            double leftY = gamepad1.left_stick_y * -1;
            //driveTrain.translate(calcTranslatePower(leftX),calcTranslatePower(leftY));
            //telemetry.addData("drive power", driveTrain.translate(true,leftX,leftY,telemetry));
            driveTrain.translate(true, leftX, leftY, telemetry);

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            driveTrain.rotate(calcRotatePower(leftTrigger), calcRotatePower(rightTrigger));

            wheelPosition = driveTrain.getWheelPosition();
            positionFinder.updatePosition();

            telemetry.addData("" + leftX,leftY);
            telemetry.addData("wheel positions", ":::::::::::::");
            telemetry.addData("lfd",wheelPosition[0]);
            telemetry.addData("lrd",wheelPosition[1]);
            telemetry.addData("rrd",wheelPosition[2]);
            telemetry.addData("rfd",wheelPosition[3]);
            telemetry.addData("powers",driveTrain.getMotorPower(0) + " " + driveTrain.getMotorPower(1),6);
            telemetry.addData(""+driveTrain.getMotorPower(2),driveTrain.getMotorPower(3));
            telemetry.addData("Drive Position", positionFinder.getPosition());
            telemetry.update();
        }
    }
    public double calcRotatePower (double triggerPos) {
        return triggerPos;
    }
    public String shorten (double val, int len) {
        return Position.shorten(val, len);
    }
}
