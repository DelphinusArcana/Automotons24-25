package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoWrapper {
    private Servo servo;
    protected double position;
    protected String name;
    public ServoWrapper (Servo servo, double position, String name) {
        this.servo = servo;
        this.position = position;
        this.name = name;
    }
    public void setPosition(double position) {
        this.position = position;
        servo.setPosition(position);
    }
    public String getName () {
        return name;
    }
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("Servo " + name, "Position:" + position);
    }
}
