package org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.FakeTelemetry;
/** A virtual servo */
public class ServoSim extends ServoWrapper {
    private static FakeTelemetry fakeTelemetry;

    /** CONSTRUCTOR sets all instance variables
     * @param startPosition the starting position for this
     * @param name this's name */
    public ServoSim(double startPosition, String name) {
        super (FakeHardware.fakeServo, startPosition, name);
    }
    @Override
    public void printInfo (Telemetry telemetry) {
        telemetry.addData("ServoSim " + name, "Position:" + position);
    }
    public static void runTests(Telemetry telemetry){
        ServoSim servoSim1 = new ServoSim(0, "servoSim1");
        servoSim1.printInfo(telemetry);
        servoSim1.getName();
        servoSim1.setPosition(1000.0);
        servoSim1.printInfo(telemetry);
        for(double i = 0.0; i>10000; i+=0.3){
            servoSim1.setPosition(i);
            servoSim1.printInfo(telemetry);
            telemetry.addData("correct position value ",  i);
        }
    }
}
