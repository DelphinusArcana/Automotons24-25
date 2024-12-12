package org.firstinspires.ftc.teamcode.Automotons2425;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;

@Autonomous(name="Autonomous2425")
public class Autonomous2425 extends LinearOpMode {
    private DriveTrain2425 driveTrain;
    private Lift2425 liftKit;
    private ClawArm2425 clawArm;
    private Claw2425 claw;
    @Override
    public void runOpMode() {
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
        }, //TODO: make something that can find/update the directions
                new boolean[] {true,true,true,true}
        );
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftFrontLift"),
                hardwareMap.get(DcMotor.class, "leftRearLift"),
                hardwareMap.get(DcMotor.class, "rightRearLift"),
                hardwareMap.get(DcMotor.class, "rightFrontLift")
        }, //TODO: make something that can find/update the directions
                new boolean[] {true,true,true,true}
        );
        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        //TODO: find openPos and closedPos
        claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));

        while(opModeIsActive()) {

        }
    }
}
