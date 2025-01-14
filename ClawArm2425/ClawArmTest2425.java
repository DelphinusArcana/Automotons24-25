package org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;

@TeleOp(name = "ClawArmTest2425", group = "Linear Opmode")
public class ClawArmTest2425 extends LinearOpMode {
    //variable declaration
    private ClawArm2425 clawArm;
    //move speed
    private double clawArmSpeed;
    private int saveElapsedMilli;
    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;
    private int uprightPosition;
    private ButtonWatcher2425 dpadLeft2;
    private ButtonWatcher2425 dpadRight2;
    private int zeroPosition;
    private ButtonWatcher2425 a2;
    private ButtonWatcher2425 y2;
    private double maxPower;
    private ButtonWatcher2425 b2;
    private ButtonWatcher2425 x2;
    private int maxPowerError;
    private ButtonWatcher2425 leftBumper2;
    private ButtonWatcher2425 rightBumper2;

    @Override
    public void runOpMode(){
        //variable initialize -classes
        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        zeroPosition = hardwareMap.get(DcMotor.class, "armMotor").getCurrentPosition();
        //TODO: find openPos and closedPos
        clawArmSpeed = 0.05;
        maxPowerError = 100;
        clawArm.setMaxPowerError(maxPowerError);
        maxPower = 0.5;
        uprightPosition = 0;

        dpadDown2 = new ButtonWatcher2425();
        dpadUp2 = new ButtonWatcher2425();
        dpadLeft2 = new ButtonWatcher2425();
        dpadRight2 = new ButtonWatcher2425();
        a2 = new ButtonWatcher2425();
        y2 = new ButtonWatcher2425();
        b2 = new ButtonWatcher2425();
        x2 = new ButtonWatcher2425();
        leftBumper2 = new ButtonWatcher2425();
        rightBumper2 = new ButtonWatcher2425();

        ElapsedTime runtime = new ElapsedTime();
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

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< arm stuff
            if (gamepad1.y) {
                clawArm.changeTargetPosition(clawArmSpeed * timeCoef);
                telemetry.addData("In","creasing");
            } else if (gamepad1.b){
                clawArm.changeTargetPosition(-1 * clawArmSpeed * timeCoef);
                telemetry.addData("De","creasing");
            }
            clawArm.powerArm();

            if (dpadUp2.pressed(gamepad2.dpad_up)){
                uprightPosition +=5;
                clawArm.setUprightPosition(uprightPosition);
            }if (dpadDown2.pressed(gamepad2.dpad_down)){
                uprightPosition-=5;
                clawArm.setUprightPosition(uprightPosition);
            }if (dpadLeft2.pressed(gamepad2.dpad_left)){
                zeroPosition-=5;
                clawArm.setZeroPosition(zeroPosition);
            }if (dpadRight2.pressed(gamepad2.dpad_right)){
                zeroPosition+=5;
                clawArm.setZeroPosition(zeroPosition);
            }if (a2.pressed(gamepad2.a)){
                maxPower-=0.001;
                clawArm.setMaxPower(maxPower);
            }if (y2.pressed(gamepad2.y)){
                maxPower+=0.001;
                clawArm.setMaxPower(maxPower);
            }if (b2.pressed(gamepad2.b)){
                maxPowerError-=5;
                clawArm.setMaxPowerError(maxPowerError);
            }if (x2.pressed(gamepad2.x)){
                maxPowerError+=5;
                clawArm.setMaxPowerError(maxPowerError);
            }if (leftBumper2.pressed(gamepad2.left_bumper)){
                clawArmSpeed -=0.004;
            }if (rightBumper2.pressed(gamepad2.right_bumper)){
                clawArmSpeed +=0.004;
            }

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
            telemetry.addData("Current Position",clawArm.getCurrentPosition());
            telemetry.addData("Target Position",clawArm.getTargetPosition());
            telemetry.addData("claw Arm Speed", clawArmSpeed);
            telemetry.addData("Max Power Error", maxPowerError);
            telemetry.addData("Max Power",maxPower);
            telemetry.addData("Zero position", zeroPosition);
            telemetry.addData("Upright Position", uprightPosition);
            telemetry.update();
        }
    }
}
