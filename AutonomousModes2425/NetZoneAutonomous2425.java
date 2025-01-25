package org.firstinspires.ftc.teamcode.Automotons2425.AutonomousModes2425;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.*;
import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

import java.util.ArrayList;

@Autonomous(name="NetZoneAutonomous2425")
public class NetZoneAutonomous2425 extends LinearOpMode {
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
                new boolean[] {true,true,true,true}
        );
        liftKit = new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftLift"),
                hardwareMap.get(DcMotor.class, "rightLift"),
        }, //TODO: make something that can find/update the directions
                new boolean[] {false,true}
        );
        //TODO: find actual center of the robot for equation -3*24+1/2 of robot width
        //starts against side with back wheel 1 tile +6 inches from center facing basket
        //apos is -3 tiles +half the robot(against side)
        Position currentPosition = new Position(-3*24+9, 24+6+1, 0);
        positionFinder = new PositionFinder2425(driveTrain, currentPosition);

        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        //TODO: find openPos and closedPos
        claw = new Claw2425(0, 0, hardwareMap.get(Servo.class, "clawServo"));
        //TODO: find values
        //TODO: have these be static final constants inside of the classes
        moveTolerance = 2;
        oritentationTolerance = Math.PI/24;
        armLowest = 0;
        armTolerance = 5;
        raisedArmPos = Math.PI;
        liftMax = -3600;
        liftTolerance = 10;
        liftMin = 0;
        armInBasketPos = Math.PI/2;

        ArrayList<Action2425> toDoList = new ArrayList<>();

        //first sample
        toDoList.add(new SetArm2425(clawArm,armLowest,armTolerance)); // Lowers the claw arm
        toDoList.add(new SetClaw2425(claw, true)); // Opens the claw
        toDoList.add(new SetLift2425(liftKit, liftMin, liftTolerance));
        //bpos values are 1 tile +6 to avoid triangular map piece +1 so claw reaches
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+6+1, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, Math.PI/2, oritentationTolerance, positionFinder));
        //TODO: have arm move to lowest position
        toDoList.add(new SetArm2425(clawArm, armLowest, armTolerance));
        //bpos increases by 2 so we can drive into the sample and make sure we have contact with it
        //apos is one tile + half a sample
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+1+6+2, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetClaw2425(claw, false));
        //TODO: make lifting and arm and movement a multi-action (next comp?)
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));
        //might want to orient with wall using a calibrate position
        //bpos is 2 squares +2 so claws reach
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-3*24+9, 2*24+2, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, 3*Math.PI/4, oritentationTolerance, positionFinder));
        toDoList.add(new SetLift2425(liftKit, liftMax, liftTolerance));
        toDoList.add(new SetArm2425(clawArm, armInBasketPos, armTolerance));
        toDoList.add(new SetClaw2425(claw, true));
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));

        //second sample
        toDoList.add(new SetArm2425(clawArm,armLowest,armTolerance)); // Lowers the claw arm
        toDoList.add(new SetClaw2425(claw, true)); // Opens the claw
        toDoList.add(new SetLift2425(liftKit, liftMin, liftTolerance));
        //bpos is increased by 11 from last section which is the distance from one sample to the next
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+6+1+11, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, Math.PI/2, oritentationTolerance, positionFinder));
        //TODO: have arm move to lowest position
        toDoList.add(new SetArm2425(clawArm, armLowest, armTolerance));
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+1+6+2+11, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetClaw2425(claw, false));
        //TODO: make lifting and arm and movement a multi-action (next comp?)
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));
        //might want to orient with wall using a calibrate position
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-3*24+9, 2*24+2, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, 3*Math.PI/4, oritentationTolerance, positionFinder));
        toDoList.add(new SetLift2425(liftKit, liftMax, liftTolerance));
        toDoList.add(new SetArm2425(clawArm, armInBasketPos, armTolerance));
        toDoList.add(new SetClaw2425(claw, true));
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));

        //third sample
        toDoList.add(new SetArm2425(clawArm,armLowest,armTolerance)); // Lowers the claw arm
        toDoList.add(new SetClaw2425(claw, true)); // Opens the claw
        toDoList.add(new SetLift2425(liftKit, liftMin, liftTolerance));
        //bpos is again increased by 11 from last section
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+6+1+22, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, Math.PI/2, oritentationTolerance, positionFinder));
        //TODO: have arm move to lowest position
        toDoList.add(new SetArm2425(clawArm, armLowest, armTolerance));
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-24-1.5, 24+1+6+2+22, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetClaw2425(claw, false));
        //TODO: make lifting and arm and movement a multi-action (next comp?)
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));
        //might want to orient with wall using a calibrate position
        toDoList.add(new GoToPosition2425(driveTrain,new Position(-3*24+9, 2*24+2, Math.PI/2), moveTolerance, positionFinder));
        toDoList.add(new SetOrientation2425(driveTrain, 3*Math.PI/4, oritentationTolerance, positionFinder));
        toDoList.add(new SetArm2425(clawArm, armInBasketPos, armTolerance));
        toDoList.add(new SetLift2425(liftKit, liftMax, liftTolerance));
        toDoList.add(new SetClaw2425(claw, true));
        toDoList.add(new SetArm2425(clawArm, raisedArmPos, armTolerance));

        toDoList.add(new SetLift2425(liftKit, liftMin, liftTolerance));
        telemetry.addData("status","initialized");
        waitForStart();

        while(opModeIsActive()) {
            if (toDoList.get(0).isComplete())
                toDoList.remove(0);
            else
                toDoList.get(0).doAction();
        }
    }
}

