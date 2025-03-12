package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.OldCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/** Controls a DcMotor and stores information about it */
public class MotorControl {
    /** The motor this controls */
    private DcMotor motor;
    /** The position the motor was in the previous time update() was called */
    private int lastPos;
    /** The position the motor is in */
    private int pos;
    /** CONSTRUCTOR sets all the instance variables
     * */
    public MotorControl (DcMotor motor, DcMotorSimple.Direction direction) {
        this.motor = motor;
        this.motor.setDirection(direction);
        pos = motor.getCurrentPosition();
        update();
    }
    /** Updates the instance variables to reflect the current state of the motor. Sets the motor's power to 0. */
    public void update () {
        lastPos = pos;
        pos = motor.getCurrentPosition();
        motor.setPower(0.0);
    }
    /** Calculates how far the motor has moved since the previous time update() was called */
    public int distanceTraveled () {
        return pos - lastPos;
    }
    public int getPos () {
        return pos;
    }
    public void addPower (double power) {
        motor.setPower(motor.getPower() + power);
    }
    public double getPower () {
        return motor.getPower();
    }
}
