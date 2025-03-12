package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.AutonomousModes2425;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.Action2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.FakeTelemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.Claw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Claw2425.SpinClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

import java.util.ArrayList;

// TODO: see if we need an @Autonomous here
public abstract class AbstractAutonomous extends LinearOpMode {
    protected DriveTrain2425 driveTrain;
    protected Lift2425 liftKit;
    protected ClawArm2425 clawArm;
    protected Claw2425 claw;
    protected SpinClaw2425 spinClaw;
    protected PositionFinder2425 positionFinder;
    protected ArrayList<Action2425> toDoList;
    @Override
    public void runOpMode() {
        driveTrain = DriveTrain2425.defaultDrive(hardwareMap);
        liftKit = Lift2425.defaultLift(hardwareMap);
        clawArm = ClawArm2425.defaultArm(hardwareMap);
        claw = Claw2425.defaultClaw(hardwareMap);
        spinClaw = SpinClaw2425.defaultSpinClaw(hardwareMap);
        positionFinder = new PositionFinder2425(driveTrain, getStartPosition());
        toDoList = makeToDoList();
        telemetry.addData("status","initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (toDoList.get(0).isComplete())
                toDoList.remove(0);
            else
                toDoList.get(0).doAction();
            extraRunningStuff();
            liftKit.powerMotors();
            clawArm.powerArm(FakeTelemetry.fake);
            positionFinder.updatePosition();
            telemetry.addData("Position", positionFinder.getPosition());
            telemetry.addData("Arm Current Position",clawArm.getCurrentPosition());
            telemetry.addData("Current Lift Height", liftKit.getAverageHeight());
            telemetry.addData("Target Lift Height", liftKit.getTargetHeight());
            telemetry.update();
        }
    }
    public abstract ArrayList<Action2425> makeToDoList();
    public abstract Position getStartPosition();
    public abstract void extraRunningStuff();
}
