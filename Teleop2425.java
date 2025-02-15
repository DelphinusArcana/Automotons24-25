package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.SpinClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;

@TeleOp(name = "a1Teleop2425", group = "Linear Opmode")
public class Teleop2425 extends LinearOpMode {
    //variable declaration - classes
    private DriveTrain2425 driveTrain;
    private Lift2425 liftKit;
    private ClawArm2425 clawArm;
    private SpinClaw2425 spinClaw;
    //private Claw2425 claw;
    //variable declaration - variable
    private double liftSpeed; //coefficient to adjust how much lift target moves each loop
    private int liftMaxHeight; //upperbound of liftkit position
    private int liftMinimumHeight; //lower bound of liftkit position- might be unneeded if equals 0
    private int moveSpeed; //coefficient that changes driveTrain translation values
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private double minTranslatePower; //minimum move speed
    private double clawArmSpeed;
    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;
    private ButtonWatcher2425 liftKitUp;
    private ButtonWatcher2425 lBumper;
    private ButtonWatcher2425 rBumper;
    private double raisedArmPos;
    private double liftMax;
    private double liftMin;
    private double armInBasketPos;

    @Override
    public void runOpMode(){
        //variable initialize -classes
        driveTrain = DriveTrain2425.defaultDrive(hardwareMap);
        liftKit = Lift2425.defaultLift(hardwareMap);
        clawArm = ClawArm2425.defaultArm(hardwareMap);
        spinClaw = SpinClaw2425.defaultSpinClaw(hardwareMap);
        //claw = Claw2425.defaultClaw(hardwareMap);
        //variable initialize - variables
        liftSpeed = 0.8;
        liftMaxHeight = 0;
        liftMinimumHeight = -3600;
        moveSpeed = 1;
        minTranslatePower = 0.25;
        clawArmSpeed = -0.05;
        raisedArmPos = 62;
        liftMax = -3600;
        liftMin = 0;
        armInBasketPos = 100;
        ButtonWatcher2425 leftTrigger2 = new ButtonWatcher2425();
        dpadUp2 = new ButtonWatcher2425();
        dpadDown2 = new ButtonWatcher2425();
        ButtonWatcher2425 aButton2 = new ButtonWatcher2425();
        ButtonWatcher2425 bButton2 = new ButtonWatcher2425();
        ButtonWatcher2425 lBumper = new ButtonWatcher2425();
        ButtonWatcher2425 rBumper = new ButtonWatcher2425();
        ButtonWatcher2425 liftKitUp = new ButtonWatcher2425();
        boolean doMinMaxLimit = true;
        ButtonWatcher2425 leftX2Pos = new ButtonWatcher2425();
        ButtonWatcher2425 leftX2Neg = new ButtonWatcher2425();
        ButtonWatcher2425 leftY2Pos = new ButtonWatcher2425();
        ButtonWatcher2425 leftY2Neg = new ButtonWatcher2425();
        ButtonWatcher2425 rightX2Pos = new ButtonWatcher2425();
        ButtonWatcher2425 rightX2Neg = new ButtonWatcher2425();
        ButtonWatcher2425 rightY2Pos = new ButtonWatcher2425();
        ButtonWatcher2425 rightY2Neg = new ButtonWatcher2425();
        int liftCalSmall = 1;
        int liftCalLarge = 10;
        ButtonWatcher2425 rightTrigger2 = new ButtonWatcher2425();
        boolean liftAtHighPower = false;
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
            //change lift kit target
            double rightY = gamepad1.right_stick_y;

            liftKit.changeTargetHeight(rightY * liftSpeed * timeCoef);

            //might be added to liftkit class
            if (liftKit.getTargetHeight() > liftMaxHeight && rightY > 0 && doMinMaxLimit) {
                liftKit.setTargetHeight(liftMaxHeight);
            }
            if (liftKit.getTargetHeight() < liftMinimumHeight && rightY < 0 && doMinMaxLimit){
                liftKit.setTargetHeight(liftMinimumHeight);
            }
            //makes the lift kit motors turn
            liftKit.powerMotors();
            telemetry.addData("Lift height",liftKit.getAverageHeight());
            telemetry.addData("Lift Target",liftKit.getTargetHeight());

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< drive train control
            double leftX = gamepad1.left_stick_x * -1.0;
            double leftY = gamepad1.left_stick_y * -1.0;
            /*
            driveTrain.translate(true, leftX, leftY, telemetry);
             */

            double leftTrigger = gamepad1.left_trigger;
            double rightTrigger = gamepad1.right_trigger;
            /*
            if (Math.abs(leftTrigger) > 0.1 || Math.abs(rightTrigger) > 0.1)
                driveTrain.rotate(calcRotatePower(leftTrigger), calcRotatePower(rightTrigger));
            telemetry.addData("Left Trigger", leftTrigger);
            telemetry.addData("Right Trigger", rightTrigger);
            */
            driveTrain.translateAndRotate(leftTrigger,rightTrigger,leftX,leftY,telemetry);

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< spinning claw
            if (rBumper.pressed(gamepad1.right_bumper)) {
                if (spinClaw.getDirection()==1) {
                    spinClaw.stop();
                } else {
                    spinClaw.pull();
                }
            }
            if (lBumper.pressed(gamepad1.left_bumper)) {
                if (spinClaw.getDirection()==-1) {
                    spinClaw.stop();
                } else {
                    spinClaw.push();
                }
            }
/*
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< claw stuff
            if (gamepad1.right_bumper && !rBumpPressed) {
                claw.toggleClaw();
                rBumpPressed = true;
            } else if (!gamepad1.right_bumper) {
                rBumpPressed = false;
            }*/

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< arm stuff
            if (gamepad1.dpad_down) {
                clawArm.changeTargetPosition(clawArmSpeed*timeCoef);
            } else if (gamepad1.dpad_up){
                clawArm.changeTargetPosition(-1*clawArmSpeed*timeCoef);
            }
            clawArm.powerArm(telemetry);
            //<<<<<<<<<<<<<<<<<<<<<<<<<presets
            if(gamepad1.a){
               clawArm.setTargetPosition(armInBasketPos);
            }
            if(gamepad1.b){
                clawArm.setTargetPosition(raisedArmPos);
            }
            /*if (liftKitUp.pressed(liftKit.getCurrentPosition(0)<liftMax*2/3) && !(gamepad1.a||gamepad1.b||gamepad1.dpad_down||gamepad1.dpad_up)) {
                clawArm.setTargetPosition(raisedArmPos);
            }*/
            if(gamepad1.y){
                liftKit.setTargetHeight(liftMaxHeight);
            }
            if(gamepad1.x){
                liftKit.setTargetHeight(liftMinimumHeight);
            }

            //<<<<<<<<<<<<<<<<<<<<<<<<<<< Calibration -- gamepad 2
            /*// Claw Calibration
            if (dpadUp2.pressed(gamepad2.dpad_up)){
                claw.shiftPositions((double) -1 /32);
            }
            if (dpadDown2.pressed(gamepad2.dpad_down)){
                claw.shiftPositions((double) 1 /32);
            }
            telemetry.addData("Claw Open Pos", claw.getOpenPosition());
            telemetry.addData("Claw Closed Pos", claw.getClosedPosition());*/
            // Lift Calibration
            if (leftTrigger2.pressed(gamepad2.left_trigger >= 0.5)) { // Turn safeties on/off
                doMinMaxLimit = !doMinMaxLimit;
            }
            if (leftX2Neg.pressed(gamepad2.left_stick_x <= -0.9)) {
                liftKit.increaseStartPos(0,-1 * liftCalSmall);
            }
            if (leftX2Pos.pressed(gamepad2.left_stick_x >= 0.9)) {
                liftKit.increaseStartPos(0,liftCalSmall);
            }
            if (leftY2Neg.pressed(gamepad2.left_stick_y <= -0.9)) {
                liftKit.increaseStartPos(0,-1 * liftCalLarge);
            }
            if (leftY2Pos.pressed(gamepad2.left_stick_y >= 0.9)) {
                liftKit.increaseStartPos(0,liftCalLarge);
            }
            if (rightX2Neg.pressed(gamepad2.right_stick_x <= -0.9)) {
                liftKit.increaseStartPos(1,-1 * liftCalSmall);
            }
            if (rightX2Pos.pressed(gamepad2.right_stick_x >= 0.9)) {
                liftKit.increaseStartPos(1,liftCalSmall);
            }
            if (rightY2Neg.pressed(gamepad2.right_stick_y <= -0.9)) {
                liftKit.increaseStartPos(1,-1 * liftCalLarge);
            }
            if (rightY2Pos.pressed(gamepad2.right_stick_y >= 0.9)) {
                liftKit.increaseStartPos(1,liftCalLarge);
            }
            if (liftKitUp.pressed(gamepad2.triangle)) {
                liftKit.zeroMotors();
            }
            if (rightTrigger2.pressed(gamepad2.right_trigger >= 0.5)) {
                liftAtHighPower = !liftAtHighPower;
                if (liftAtHighPower) {
                    liftKit.setMaxPowerError(100);
                    liftKit.setMaxPower(1.0);
                } else {
                    liftKit.setMaxPower(0.7);
                    liftKit.setMaxPowerError(40);
                }
            }
            telemetry.addData("Left x",leftX);
            telemetry.addData("Left y",leftY);

            telemetry.addData("Lift at high power",liftAtHighPower);
            telemetry.addData("Do min max lift", doMinMaxLimit);
            telemetry.addData("Lift Left Start",liftKit.getStartPosition(0));
            telemetry.addData("Lift Right Start",liftKit.getStartPosition(1));
            telemetry.addData("Claw Current Position",clawArm.getCurrentPosition());
            // Arm Calibration
            if (aButton2.pressed(gamepad2.cross)) { // Swap claw arm direction
                clawArmSpeed *= -1;
            }
            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
            telemetry.update();
        }
    }
    public double calcTranslatePower (double stickPos) {
        return (stickPos*(1-minTranslatePower)+Math.signum(stickPos)*minTranslatePower)*moveSpeed;
    }
    public double calcRotatePower (double triggerPos) {
        if (Math.abs(triggerPos) != 1) {
            triggerPos /= 2;
        }
        return triggerPos;
    }
}
