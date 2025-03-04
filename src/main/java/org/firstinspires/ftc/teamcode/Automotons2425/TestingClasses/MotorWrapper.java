package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Controls a motor. Hopefully has all methods a motor uses. */
public class MotorWrapper {
    /** The motor this wraps*/
    private DcMotor motor;
    /** This motor/motorWrapper's name*/
    private String name;
    /** The power being sent to the motor*/
    protected double power;
    /** True if this motor should be set to forward, false if reverse */
    protected boolean isForward;
    /** CONSTRUCTOR sets all instance variables.
     * direction defaults to forward.
     * Power starts at 0.
     * @param motor the motor this controls
     * @param name this motor/motorWrapper's name*/
    public MotorWrapper(DcMotor motor, String name) {
        this.motor = motor;
        this.name = name;
        power = 0;
        setDirection(true); // default to forward, but should be set.
    }
    /** Displays relevant info for this motorWrapper, including name, power, direction, and position.
     * @param telemetry the telemetry to send the data to*/
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("Motor " + name, "pow:" + power + " direction:" + isForward + " position:" + motor.getCurrentPosition());
    }
    public void setDirection (boolean isForward) {
        this.isForward = isForward;
        if (isForward)
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setPower (double power) {
        this.power = power;
        motor.setPower(power);
    }
    public double getPower () {
        return power;
    }
    public int getCurrentPosition () {
        return motor.getCurrentPosition();
    }
    public String getName () {
        return name;
    }
}
