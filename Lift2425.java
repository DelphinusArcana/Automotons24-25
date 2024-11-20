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

/** PLEASE NOTE: if you are using this class, you MUST call powerMotors() each time through your code (such as the while(opModeIsActive()) loop in a TeleOp)*/
public class Lift2425 {
    /** The height at which the lift should be */
    private int targetHeight;
    /** Array of 4 start positions used to calculate the currentHeight. 0 is front left motor, counting counter clockwise from there*/
    private int[] startPositions;
    /** Array of 4 motors that control the lift kit
     * Indexes: 0 is front left motor, counting counterclockwise */
    private DcMotor[] motors;
    /** CONSTRUCTOR
     * Initializes motors
     * motors.length == 4
     */
    public Lift2425 (DcMotor[] motors) {
        this.motors = motors;
        targetHeight = 0;
        startPositions = new int[motors.length];
        for (int i = 0; i < motors.length; i++) {
            startPositions[i] = motors[i].getCurrentPosition();
        }
    }
    /** Sets the target height
     * param height the lift should be
     */
    public void setTargetHeight (int height) {
        targetHeight = height;
    }
    /** Incrementally sets the distance for the lift to go amount
     * @param increase the amount the target height should change */
    public void changeTargetHeight (int increase) {
        targetHeight += increase;
    }
    /** Powers the motors based on current power and updates the current height */
    public void powerMotors () {

    }
    /** Calculates the power of a motor based on the difference between current position and target height */
    public double calculatePower (DcMotor motor) {

    }
    /** Calculates the difference between the current height and the target height */
    private int getCurrentOffset (int motorIndex) {
        return motors[motorIndex].getCurrentPosition() - startPositions[motorIndex];
    }
    /** Powers the motors such that they are in the same position */
    public void syncMotors () {

    }
    /** Sets zero position of a motor */
    public void updateMotorStartPosition (int motorIndex, int newStartposition) {
        startPositions[motorIndex] = newStartposition;
    }
    /** Sets the start positions of each motor to its current position */
    public void zeroMotors() {
        for (int i = 0; i < motors.length; i++) {
            startPositions[i] = motors[i].getCurrentPosition();
        }
    }
    /** Average distance from the motors’ starting position and current position */
    public int getAverageHeight () {

    }
}
