package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

public class ServoSim extends ServoWrapper {
    public ServoSim(double startPosition, String name) {
        super (FakeHardware.fakeServo, startPosition, name);
    }
}
