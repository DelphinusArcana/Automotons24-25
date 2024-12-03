package org.firstinspires.ftc.Automotons;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="George", group="Linear Opmode")
public class george extends LinearOpMode {
    private Servo testServo;
    private final double baseAngle = 0.5;
    private final double extent= 0.25;
    private int direction = 1;
    private double step = 0.0001;
    private double angle;
    private double time;
    private double wantAngle;
    private final double tolerance = 0.0001;
    @Override
    public void runOpMode () {
        testServo = hardwareMap.get(Servo.class, "testServo");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        testServo.setPosition(baseAngle);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("direction", direction);
            telemetry.addData("servoPosition", testServo.getPosition());
            time = System.currentTimeMillis();
            angle = testServo.getPosition();
            wantAngle=angle+direction*step;
            if (gamepad1.left_bumper) {
                testServo.setPosition(wantAngle);
            } else {
                if (direction==1) {
                    if (angle>=baseAngle+extent-tolerance) {
                        direction*=-1;
                    }
                } else if (angle<=baseAngle-extent+tolerance) {
                    direction*=-1;
                }
                testServo.setPosition(baseAngle+direction*extent);
            }

            telemetry.update();
        }
    }

}

