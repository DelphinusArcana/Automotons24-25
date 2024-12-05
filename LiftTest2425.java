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

@TeleOp(name = "LiftOnly2425", group = "Liniar Opmode")
public class LiftTest2425 extends LinearOpMode {
    //variable declaration - classes
    private Lift2425 liftKit;
    //variable declaration - variable
    private double liftSpeed; //coefficient to adjust how much lift target moves each loop
    private int liftMaxHeight; //upperbound of liftkit position
    private int liftMinimumHeight; //lower bound of liftkit position- might be unneeded if equals 0
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private double liftMaxPower;
    private int liftMaxPowerError;
    private boolean dPadUpPressed;
    private boolean dPadDownPressed;
    private boolean dPadLeftPressed;
    private boolean dPadRightPressed;
    private boolean aPressed;
    private boolean yPressed;
    private boolean dPadUpPressed2;
    private boolean dPadDownPressed2;
    private boolean aPressed2;
    private boolean yPressed2;

    @Override
    public void runOpMode(){
        //variable initialize -classes
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftFrontLift"),
                hardwareMap.get(DcMotor.class, "leftRearLift"),
                hardwareMap.get(DcMotor.class, "rightRearLift"),
                hardwareMap.get(DcMotor.class, "rightFrontLift")
        }, //TODO: make something that can find/update the directions
                new boolean[] {true,true,true,true}
        );
        //variable initialize - variables
        liftSpeed = 0.5;
        liftMaxHeight = 100000;
        liftMinimumHeight = 0;
        liftMaxPower = 0.5;
        liftMaxPowerError = 500;

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

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<liftkit controll
            //TODO: see if we want more precise control on claw arm or lift kit (maybe switch stick for buttons)
            //change lift kit target
            double rightY = gamepad1.right_stick_y;

            //might be added to liftkit class
            if (liftKit.getAverageHeight() + (rightY * liftSpeed * timeCoef)>liftMaxHeight){
                liftKit.setTargetHeight(liftMaxHeight);
            }
            else if (liftKit.getAverageHeight() + (rightY * liftSpeed * timeCoef)<liftMinimumHeight){
                liftKit.setTargetHeight(liftMinimumHeight);
            }
            else {
                liftKit.changeTargetHeight(rightY * liftSpeed * timeCoef);
            }
            //lift kit calibration
            //liftSpeed liftMaxHeight liftMinHeight
            if (gamepad1.dpad_up && !dPadUpPressed){
                dPadUpPressed = true;
                liftSpeed+= 0.025;
            }else if (!gamepad1.dpad_up){
                dPadUpPressed = false;
            }
            if (gamepad1.dpad_down && !dPadDownPressed){
                dPadDownPressed = true;
                liftSpeed-=0.025;
            }else if (!gamepad1.dpad_down){
                dPadDownPressed = false;
            }


            if (gamepad1.dpad_right && !dPadRightPressed){
                dPadRightPressed = true;
                liftMaxHeight +=1000;
            }else if (!gamepad1.dpad_right){
                dPadRightPressed = false;
            }
            if (gamepad1.dpad_left && !dPadLeftPressed){
                dPadLeftPressed = true;
                liftMaxHeight -=1000;
            }else if (!gamepad1.dpad_left){
                dPadLeftPressed = false;
            }

            if (gamepad1.a && !aPressed){
                aPressed = true;
                liftMinimumHeight+=1000;
            }else if (!gamepad1.a){
                aPressed = false;
            }if (gamepad1.y && !yPressed){
                yPressed = true;
                liftMinimumHeight+=1000;
            }else if (!gamepad1.y){
                yPressed = false;
            }

            if (gamepad2.a && !aPressed2){
                aPressed2 = true;
                liftMaxPower -=0.02;
            }else if (!gamepad2.a){
                aPressed2 = false;
            }
            if (gamepad2.y && !yPressed2){
                yPressed2 = true;
                liftMaxPower+=0.02;
            }else if (!gamepad2.y){
                yPressed2 = false;
            }
            liftKit.setMaxPower(liftMaxPower);


            if (gamepad2.dpad_up && !dPadUpPressed2){
                dPadUpPressed2 = true;
                liftMaxPowerError+=10;
            }else if (!gamepad2.dpad_up){
                dPadUpPressed2 = false;
            }if (gamepad2.dpad_down && !dPadDownPressed2){
                dPadDownPressed2 = true;
                liftMaxPowerError-=10;
            }else if (!gamepad2.dpad_down){
                dPadDownPressed2 = false;
            }
            liftKit.setMaxPowerError(liftMaxPowerError);


            liftKit.powerMotors();

            telemetry.addData("max height", liftMaxHeight);
            telemetry.addData("min height", liftMinimumHeight);
            telemetry.addData("speed", liftSpeed);
            telemetry.addData("max Power", liftMaxPower);
            telemetry.addData("max Power Error", liftMaxPowerError);

            telemetry.update();
        }
    }
}
