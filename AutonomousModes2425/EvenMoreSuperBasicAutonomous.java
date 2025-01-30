package org.firstinspires.ftc.teamcode.Automotons2425.AutonomousModes2425;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.Action2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.FakeTelemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.GoToPosition2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetLift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

import java.util.ArrayList;

@Autonomous(name="ObservationZoneAuto")
public class EvenMoreSuperBasicAutonomous extends LinearOpMode {
    private DriveTrain2425 driveTrain;
    private Lift2425 liftKit;
    private ClawArm2425 clawArm;
    private Claw2425 claw;
    private PositionFinder2425 positionFinder;
    private double oritentationTolerance;
    private double moveTolerance;
    private int armTolerance;
    private double armLowest;
    private double raisedArmPos;
    private double liftMax;
    private double liftTolerance;
    private double liftMin;
    private double armInBasketPos;
    @Override
    public void runOpMode() {
        driveTrain = new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
        },//TODO: make something that can find/update the directions
                new boolean[] {false,true,false,true}
        );
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftLift"),
                hardwareMap.get(DcMotor.class, "rightLift"),
        }, //TODO: make something that can find/update the directions
                new boolean[] {false,false}
        );
        //TODO: find actual center of the robot for equation -3*24+1/2 of robot width
        //starts against side with back wheel 1 tile +6 inches from center facing basket
        //apos is -3 tiles +half the robot(against side)
        Position currentPosition = new Position(-3*24+9, -24, Math.PI/2);
        positionFinder = new PositionFinder2425(driveTrain, currentPosition);

        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        //TODO: find openPos and closedPos
        claw = new Claw2425(0.3515625, 0.7265625, hardwareMap.get(Servo.class, "clawServo"));
        //TODO: find values
        //TODO: have these be static final constants inside of the classes
        moveTolerance = 2;
        oritentationTolerance = Math.PI/24;
        armLowest = 139;
        armTolerance = 5;
        double semiArmPos = 100;
        raisedArmPos = 62;
        liftMax = -3600;
        liftTolerance = 10;
        liftMin = 0;
        armInBasketPos = Math.PI/2;

        ArrayList<Action2425> toDoList = new ArrayList<>();
        //Go to observation zone
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-3*24+9, -72, Math.PI/2), moveTolerance, positionFinder));



        telemetry.addData("status","initialized");
        waitForStart();

        while(opModeIsActive()) {
            if (toDoList.get(0).isComplete())
                toDoList.remove(0);
            else
                toDoList.get(0).doAction();
            liftKit.powerMotors();
            clawArm.powerArm(FakeTelemetry.fake);
            telemetry.addData("Position",positionFinder.getPosition().toString());
            telemetry.update();
        }
    }
}
