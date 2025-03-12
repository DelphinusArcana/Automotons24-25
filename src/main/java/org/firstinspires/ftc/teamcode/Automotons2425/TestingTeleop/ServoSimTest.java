package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.TestingTeleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.ButtonWatcher2425;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses.ServoSim;
import org.opencv.core.Mat;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
@Disabled
@TeleOp(name = "ServoSimTest", group = "Linear Opmode")
public class ServoSimTest extends LinearOpMode {
    //variable declaration - classes
    private int saveElapsedMilli; //used for equations like: elapesed time = total time - time since I set this variable
    private ButtonWatcher2425 dpadUp2;
    private ButtonWatcher2425 dpadDown2;
    private ServoSim sim;
    private double servoPosition;
    @Override
    public void runOpMode(){
        //variable initialize -classes
        ButtonWatcher2425 DPadUp = new ButtonWatcher2425();
        ButtonWatcher2425 DPadDown = new ButtonWatcher2425();
        ButtonWatcher2425 aButton = new ButtonWatcher2425();
        ButtonWatcher2425 bButton = new ButtonWatcher2425();
        ElapsedTime runtime = new ElapsedTime();
        servoPosition = 0;
        sim = new ServoSim(servoPosition, "sim");
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
            servoPosition = sim.getPosition();

            //Increment the servo position
            double leftY = gamepad1.left_stick_y * -1.0;
            servoPosition += 5 * leftY;
            /*
            * Left Stick moves the target position up and down
            * D Pad Up increases the position by 10
            * D Pad Down decreases the position by 10
            * A Button sets the position to 100
            * B Button sets the position to -100
            */
            if (DPadUp.pressed(gamepad1.dpad_up))
                servoPosition += 10;
            if (DPadDown.pressed(gamepad1.dpad_down))
                servoPosition -= 10;
            if (aButton.pressed(gamepad1.a))
                servoPosition = 100;
            if (bButton.pressed(gamepad1.b))
                servoPosition = -100;

            sim.setPosition(servoPosition);

            sim.printInfo(telemetry);

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< telemetry
            telemetry.update();
        }
    }
}
