package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/** A virtual servo */
public class ServoSim extends ServoWrapper {
    /** CONSTRUCTOR sets all instance variables
     * @param startPosition the starting position for this
     * @param name this's name*/
    public ServoSim(double startPosition, String name) {
        super (FakeHardware.fakeServo, startPosition, name);
    }
    @Override
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("ServoSim " + name, "Position:" + position);
    }
}
