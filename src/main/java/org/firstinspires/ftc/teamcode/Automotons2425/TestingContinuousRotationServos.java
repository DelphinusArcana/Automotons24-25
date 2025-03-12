package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;
import org.opencv.core.Mat;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "Testing Continuous Rotation", group = "Linear Opmode")
public class TestingContinuousRotationServos extends LinearOpMode {
    private CRServo tcr;
    private double power;
    private ButtonWatcher2425 a1;
    private ButtonWatcher2425 dPadUp1;
    private ButtonWatcher2425 dPadDown1;

    @Override
    public void runOpMode() {
        tcr = hardwareMap.crservo.get("coolServo");
        power = 1;
        a1 = new ButtonWatcher2425();
        dPadUp1 = new ButtonWatcher2425();
        dPadDown1 = new ButtonWatcher2425();

        // Tells the driver that the robot is ready
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        tcr.setPower(power);
        while (opModeIsActive()) {
            if (a1.pressed(gamepad1.a)) {
                power*=-1;
                tcr.setPower(power);
            }
            if (dPadUp1.pressed(gamepad1.dpad_up)) {
                power+=0.1;
                tcr.setPower(power);
            }
            if (dPadDown1.pressed(gamepad1.dpad_down)) {
                power-=0.1;
                tcr.setPower(power);
            }

            telemetry.addData("Servo Power",power);
            telemetry.update();
        }
    }
}