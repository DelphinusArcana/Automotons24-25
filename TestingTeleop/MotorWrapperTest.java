package org.firstinspires.ftc.teamcode.Automotons2425.TestingTeleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses.MotorWrapper;
import org.opencv.core.Mat;

@TeleOp(name = "MotorWrapperTest", group = "Linear Opmode")
public class MotorWrapperTest extends LinearOpMode {
    //variable declaration - classes
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;
    private DcMotor motor;
    private MotorWrapper wrapper;
    @Override
    public void runOpMode(){
        //variable initialize -classes
        ButtonWatcher2425 DPadUp = new ButtonWatcher2425();
        ButtonWatcher2425 DPadDown = new ButtonWatcher2425();
        ButtonWatcher2425 aButton = new ButtonWatcher2425();
        ButtonWatcher2425 bButton = new ButtonWatcher2425();
        ElapsedTime runtime = new ElapsedTime();
        motor = hardwareMap.get(DcMotor.class, "motor");
        wrapper = new MotorWrapper(motor, "motor");
        runtime.reset();

        // Tells the driver that the robot is ready
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        saveElapsedMilli = (int) runtime.milliseconds();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //get time since loop was last looped
            int currentMilli = (int) runtime.milliseconds();
            int timeCoef = currentMilli-saveElapsedMilli;
            saveElapsedMilli = currentMilli;

            //change lift kit target
            double leftY = gamepad1.left_stick_y * -1.0;
            wrapper.setPower(leftY);

            if (DPadUp.pressed(gamepad1.dpad_up))
                wrapper.setPower(2);
            if (DPadDown.pressed(gamepad1.dpad_down))
                wrapper.setPower(-2);
            if (aButton.pressed(gamepad1.a))
                wrapper.setDirection(true);
            if (bButton.pressed(gamepad1.b))
                wrapper.setDirection(false);

            telemetry.addData("Power", wrapper.getPower());
            telemetry.addData("Position", wrapper.getCurrentPosition());

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
            telemetry.update();
        }
    }
}
