package org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.Action2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetOrientation2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "SetOrientationTest2425", group = "Linear OpMode")
public class SetOrientationTest2425 extends LinearOpMode {
    DriveTrain2425 driveTrain;
    PositionFinder2425 positionFinder;
    ButtonWatcher2425 aButton;
    ButtonWatcher2425 dPadUp;
    ButtonWatcher2425 dPadDown;
    private double targetAngle;
    private double tolerance;
    private double speed;
    @Override
    public void runOpMode () {
        aButton = new ButtonWatcher2425();
        dPadUp = new ButtonWatcher2425();
        dPadDown = new ButtonWatcher2425();
        tolerance = 0;
        speed = Math.PI/200;
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
            },
                new boolean[] {true,true,true,true}
        );
        positionFinder = new PositionFinder2425(driveTrain, new Position(0, 0, 0));
        targetAngle = 0;

        // Tells the driver that the robot is ready
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        Action2425 action = new SetOrientation2425(driveTrain, 0, tolerance*Math.PI/500, positionFinder);

        while (opModeIsActive()) {
            targetAngle += gamepad1.left_trigger * speed;
            targetAngle -= gamepad1.right_trigger * speed;
            if (gamepad1.dpad_up) {
                tolerance += 1;
            } if (gamepad1.dpad_down) {
                tolerance -= 1;
            }
            if (aButton.pressed(gamepad1.a)) {
                action = new SetOrientation2425(driveTrain, targetAngle, tolerance*Math.PI/500, positionFinder);
            }
            action.doAction();
            telemetry.addData("Target Angle", targetAngle%(2*Math.PI));
            telemetry.addData("Tolerance (n * PI/500)", tolerance);
            telemetry.addData("Action is Complete", action.isComplete());
            telemetry.update();
        }
    }
}
