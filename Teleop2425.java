package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;

@TeleOp(name = "teleop2425", group = "Linear Opmode")
public class Teleop2425 extends LinearOpMode {
    //variable declaration - classes
    private DriveTrain2425 driveTrain;
    private Lift2425 liftKit;
    private ClawArm2425 clawArm;
    private Claw2425 claw;
    //variable declaration - variable
    private double liftSpeed; //coefficient to adjust how much lift target moves each loop
    private int liftMaxHeight; //upperbound of liftkit position
    private int liftMinimumHeight; //lower bound of liftkit position- might be unneeded if equals 0
    private boolean doMinMaxLimit; //true to restrict the liftkit movement with bounds. Should be true normally.
    private int moveSpeed; //coefficient that changes driveTrain translation values
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private double minTranslatePower; //minimum move speed
    private double clawArmSpeed;
    private double clawZeroPosition;

    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;
    @Override
    public void runOpMode(){
        //variable initialize -classes
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
                }, //TODO: make something that can find/update the directions
                new boolean[] {true,true,true,true}
        );
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftLift"),
                hardwareMap.get(DcMotor.class, "rightLift")
                }, //TODO: make something that can find/update the directions
                new boolean[] {false,false}
                );
        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        //TODO: find openPos and closedPos
        claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));
        clawZeroPosition = hardwareMap.get(DcMotor.class, "armMotor").getCurrentPosition();
        //variable initialize - variables
        liftSpeed = 0.8;
        liftMaxHeight = 0;
        liftMinimumHeight = -3600;
        liftKit.setMaxPower(0.5);
        liftKit.setMaxPowerError(70);
        doMinMaxLimit = true;

        clawArmSpeed = 0.05;
        clawArm.setMaxPowerError(10);
        clawArm.setMaxPower(0.5);
        clawArm.setUprightPosition(10); //TODO: Figure out this number

        moveSpeed = 1;
        minTranslatePower = 0.25;
        clawArmSpeed = 0.05;
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

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<liftkit control
            //TODO: see if we want more precise control on claw arm or lift kit (maybe switch stick for buttons)
            double rightY = gamepad1.right_stick_y;
            //change lift kit target
            liftKit.changeTargetHeight(rightY * liftSpeed * timeCoef);
            //might be added to liftkit class. Bounds the positions that can be desired.
            if (liftKit.getTargetHeight() > liftMaxHeight && rightY > 0 && doMinMaxLimit) { //TODO: This could be wrong since the max is negative...
                liftKit.setTargetHeight(liftMaxHeight);
            }
            if (liftKit.getTargetHeight() < liftMinimumHeight && rightY < 0 && doMinMaxLimit){
                liftKit.setTargetHeight(liftMinimumHeight);
            }
            //makes the motors turn
            liftKit.powerMotors();

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,drive train control
            double leftX = gamepad1.left_stick_x;
            double leftY = gamepad1.left_stick_y;
            driveTrain.translate(true, calcTranslatePower(leftX),calcTranslatePower(leftY), telemetry);

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            driveTrain.rotate(calcRotatePower(leftTrigger), calcRotatePower(rightTrigger));

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< claw stuff
            if (gamepad1.right_bumper && !rBumpPressed) {
                claw.toggleClaw();
                rBumpPressed = true;
            } else if (!gamepad1.right_bumper) {
                rBumpPressed = false;
            }

            if (dpadUp2.pressed(gamepad2.dpad_up)){
                claw.shiftPositions((double) -1 /32);
            }
            if (dpadDown2.pressed(gamepad2.dpad_down)){
                claw.shiftPositions((double) 1 /32);
            }

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< arm stuff
            if (gamepad1.y) {
                clawArm.changeTargetPosition(clawArmSpeed*timeCoef);
            } else if (gamepad1.b){
                clawArm.changeTargetPosition(-1*clawArmSpeed*timeCoef);
            }
            clawArm.powerArm(telemetry);

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
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
