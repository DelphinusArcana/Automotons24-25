package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import org.firstinspires.ftc.robotcore.external.Telemetry;
/** A virtual motor */
public class MotorSim extends MotorWrapper {
    private int currentPosition;
    public MotorSim (int startPosition, String name) {
        super(FakeHardware.fakeMotor, name);
        currentPosition = startPosition;
    }
    // TODO: make a proper simulation
    public void update(int timeCoeff) {

    }
    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }
    @Override
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("MotorSim " + getName(), "pow:" + power + " direction:" + isForward + " position:" + currentPosition);
    }
}
