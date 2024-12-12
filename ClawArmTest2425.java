package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "teleop2425", group = "Liniar Opmode")
public class ClawArmTest2425 extends LinearOpMode {
    //variable declaration
    private ClawArm2425 clawArm;//minimum move speed
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
        clawArmSpeed = 0.2;
        maxPowerError = 500;
        maxPower = 0.5;
        uprightPosition = 0;
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        boolean rBumpPressed = false;

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
                clawArm.changeTargetPosition(clawArmSpeed*timeCoef);
            } else if (gamepad1.b){
                clawArm.changeTargetPosition(-1*clawArmSpeed*timeCoef);
            }
            clawArm.powerArm();

            if (dpadUp2.Pressed(gamepad2.dpad_up)){
                uprightPosition +=5;
                clawArm.setUprightPosition(uprightPosition);
            }if (dpadDown2.Pressed(gamepad2.dpad_down)){
                uprightPosition-=5;
                clawArm.setUprightPosition(uprightPosition);
            }if (dpadLeft2.Pressed(gamepad2.dpad_left)){
                zeroPosition-=5;
                clawArm.setZeroPosition(zeroPosition);
            }if (dpadRight2.Pressed(gamepad2.dpad_right)){
                zeroPosition+=5;
                clawArm.setZeroPosition(zeroPosition);
            }if (a2.Pressed(gamepad2.a)){
                maxPower-=0.001;
                clawArm.setMaxPower(maxPower);
            }if (y2.Pressed(gamepad2.y)){
                maxPower+=0.001;
                clawArm.setMaxPower(maxPower);
            }if (b2.Pressed(gamepad2.b)){
                maxPowerError-=5;
                clawArm.setMaxPowerError(maxPowerError);
            }if (x2.Pressed(gamepad2.x)){
                maxPowerError+=5;
                clawArm.setMaxPowerError(maxPowerError);
            }if (leftBumper2.Pressed(gamepad2.left_bumper)){
                clawArmSpeed -=0.004;
            }if (rightBumper2.Pressed(gamepad2.right_bumper)){
                clawArmSpeed +=0.004;
            }

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
            telemetry.addData("claw Arm Speed", clawArmSpeed);
            telemetry.addData("Max Power Error", maxPowerError);
            telemetry.addData("Max Power",maxPower);
            telemetry.addData("Zero position", zeroPosition);
            telemetry.addData("Upright Position", uprightPosition);
            telemetry.update();
        }
    }
}
