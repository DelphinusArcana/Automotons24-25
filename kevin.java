package org.firstinspires.ftc.Automotons;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Kevin", group="Linear Opmode")
public class kevin extends LinearOpMode {
    private Servo testServo;
    private final double baseAngle = 0.5;
    private final double extent = 0.25;
    private final double tolerance = 0.0001;
    private double speed=1;
    private int direction=1;
    private double angle;
    private double prevTime;
    @Override
    public void runOpMode () {
        testServo = hardwareMap.get(Servo.class, "testServo");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        testServo.setPosition(baseAngle + extent);
        time = System.currentTimeMillis();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("servoWant", testServo.getPosition());
            prevTime=time;
            time = System.currentTimeMillis();
            if (gamepad1.dpad_up) {
                speed+=0.001;
            }
            if (gamepad1.dpad_down) {
                speed-=0.001;
            }
            if (!(gamepad1.left_bumper) || !(gamepad1.right_bumper)) {
                angle+=0.002*speed*direction*(time-prevTime);
                if (direction==1 && angle>baseAngle+extent-tolerance) {
                    direction*=-1;
                }
                if (direction==-1 && angle<baseAngle-extent+tolerance) {
                    direction*=-1;
                }
            }
            testServo.setPosition(angle);
            telemetry.update();
        }
    }
}