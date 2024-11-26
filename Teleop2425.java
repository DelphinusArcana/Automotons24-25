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
public class Teleop2425 extends LinearOpMode {
    //variable declaration - classes
    private DriveTrain2425 driveTrain;
    private Lift2425 liftKit;
    private ClawArm2425 clawArm;
    private Claw2425 claw;
    //variable declaration - variable
    private int liftSpeed; //coefficient to adjust how much lift target moves each loop
    private int liftMaxHeight; //upperbound of liftkit position
    private int liftMinimumHeight; //lower bound of liftkit position- might be unneeded if equals 0
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable

    @Override
    public void runOpMode(){
        //variable initialize -classes
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
        });
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftFrontLift"),
                hardwareMap.get(DcMotor.class, "leftRearLift"),
                hardwareMap.get(DcMotor.class, "rightRearLift"),
                hardwareMap.get(DcMotor.class, "rightFrontLift")
        });
        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        //TODO: find openPos and closedPos
        claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));
        //variable initialize - variables
        liftSpeed = 500;
        liftMaxHeight = 100000;
        liftMinimumHeight = 0;

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
            int tCoef = (int) runtime.milliseconds()-saveElapsedMilli;
            saveElapsedMilli = (int) runtime.milliseconds();
            //change lift kit target
            double rightY = gamepad1.right_stick_y;

            //might be added to liftkit class
            if (liftKit.getAverageHeight() + (rightY * liftSpeed * tCoef)>liftMaxHeight){
                liftKit.setTargetHeight(liftMaxHeight);
            }
            else if (liftKit.getAverageHeight() + (rightY * liftSpeed * tCoef)<liftMinimumHeight){
                liftKit.setTargetHeight(liftMinimumHeight);
            }
            else {
                liftKit.changeTargetHeight(rightY * liftSpeed * tCoef);
            }
            //lift kit calibration

            liftKit.powerMotors();
        }
    }
}
