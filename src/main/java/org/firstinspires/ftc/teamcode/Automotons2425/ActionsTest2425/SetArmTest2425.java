package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ActionsTest2425;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetArm2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetClaw2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.SetLift2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;

import java.util.ArrayList;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "SetArmTest2425", group = "Linear OpMode")
public class SetArmTest2425 extends LinearOpMode {
    ClawArm2425 clawArm;
    boolean running;
    double desiredPosition;
    double tolerance;
    //declaring variables
    private double clawArmSpeed;
    private int saveElapsedMilli;
    private double uprightPosition;
    private double zeroPosition;
    private double maxPower;
    private int maxPowerError;

    private ButtonWatcher2425 dpadUp1;
    private ButtonWatcher2425 dpadDown1;
    private ButtonWatcher2425 dpadLeft1;
    private ButtonWatcher2425 dpadRight1;
    private ButtonWatcher2425 a1;
    private ButtonWatcher2425 y1;
    private ButtonWatcher2425 b1;
    private ButtonWatcher2425 x1;
    private ButtonWatcher2425 leftBumper1;
    private ButtonWatcher2425 rightBumper1;
    SetArm2425 action;


    @Override
    public void runOpMode () {
        //initializing variables
        clawArm = new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
        running=false;
        tolerance=10;
        ArrayList<SetArm2425> actionList = new ArrayList<SetArm2425>();
        actionList.add(new SetArm2425(clawArm,clawArm.getCurrentPosition(),tolerance));


        clawArmSpeed = 0.2;
        maxPowerError = 500;
        maxPower = 0.5;
        uprightPosition = 0;
        zeroPosition = clawArm.getCurrentPosition();

        dpadDown1 = new ButtonWatcher2425();
        dpadUp1 = new ButtonWatcher2425();
        a1 = new ButtonWatcher2425();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        while (opModeIsActive()) {
            int currentMilli = (int) runtime.milliseconds();
            int timeCoef = currentMilli-saveElapsedMilli;
            saveElapsedMilli = currentMilli;

            if (dpadDown1.pressed(gamepad1.dpad_down)) {
                tolerance-=0.2;
            }
            if (dpadUp1.pressed(gamepad1.dpad_up)) {
                tolerance+=0.2;
            }

            if (y1.pressed(gamepad1.y)) {
                actionList.add(new SetArm2425(clawArm,uprightPosition,tolerance));
            }
            if (a1.pressed(gamepad1.a)) {
                actionList.add(new SetArm2425(clawArm,zeroPosition,tolerance));
            }

            if (actionList.size()>0) {
                actionList.get(0).doAction();
                telemetry.addData("Arm moving to position",actionList.get(0).getDesiredPosition());

            }else{
                telemetry.addData("Arm not moving )","");
            }
            if (actionList.get(0).isComplete()) {
                actionList.remove(0);
                telemetry.speak("We have moved the claw");
            }

            telemetry.addData("clawArmSpeed",clawArmSpeed);
            telemetry.addData("Tolerance",tolerance);
            telemetry.update();
        }
    }

}
