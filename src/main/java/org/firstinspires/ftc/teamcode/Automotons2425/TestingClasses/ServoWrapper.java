package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** Controls and interacts with a servo. */
public class ServoWrapper {
    /** The servo this wrapper wraps */
    private Servo servo;
    /** The desired position of servo */
    protected double position;
    /** This servo/servoWrapper's name */
    protected String name;
    /** CONSTRUCTOR sets all instance variables
     * @param servo the servo this controls
     * @param position the starting position of the servo. Note that this does not set the servo's position. //TODO: this may cause problems. See if we can avoid them.
     * @param name the name of this servo/servoWrapper */
    public ServoWrapper (Servo servo, double position, String name) {
        this.servo = servo;
        this.position = position;
        this.name = name;
    }
    /** Sets the position of the servo and sets the internal variable position.
     * @param position the position to set this to*/
    public void setPosition(double position) {
        this.position = position;
        servo.setPosition(position);
    }
    public double getPosition() {
        return position;
    }
    /** Returns this's name
     * @return this's name*/
    public String getName () {
        return name;
    }
    /** Adds this's name and position to the given telemetry.
     * @param telemetry the telemetry to add the data to*/
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("Servo " + name, "Position:" + position);
    }
}
