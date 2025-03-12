package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
// TODO: write unit tests for the unit testing classes
/** A class that holds various motors and servos for the robot. Similar to something that wraps the hardwareMap, but doesn't actually do that. Works similar to the hardwareMap. */
public class TestingHardwareMap {
    ArrayList<MotorWrapper> motors;
    ArrayList<ServoWrapper> servos;
    boolean isSimulation;
    /** CONSTRUCTOR sets all instance variables
     * This constructor is designed to be good for a simulation.
     * Note that it does not copy the arraylists, so do not change them afterwards. */
    public TestingHardwareMap(ArrayList<MotorWrapper> motors, ArrayList<ServoWrapper> servos, boolean isSimulation) {
        this.motors = motors;
        this.servos = servos;
        this.isSimulation = isSimulation;
    }
    /** CONSTRUCTOR sets all instance variables
     * This constructor is designed to be good for a non-simulation. It starts all motors and servos at 0. */
    public TestingHardwareMap (HardwareMap hardwareMap, ArrayList<String> motorNames, ArrayList<String> servoNames, boolean isSimulation) {
        this.isSimulation = isSimulation;
        motors = new ArrayList<>();
        servos = new ArrayList<>();
        if (isSimulation) {
            for (String name : motorNames) {
                MotorWrapper motor = new MotorSim(0, name);
                motors.add(motor);
            }
            for (String name : servoNames) {
                ServoWrapper servo = new ServoSim(0, name);
                servos.add(servo);
            }
        } else {
            for (String name : motorNames) {
                MotorWrapper motor = new MotorWrapper(hardwareMap.get(DcMotor.class, name), name);
                motors.add(motor);
            }
            for (String name : servoNames) {
                Servo servo = hardwareMap.get(Servo.class, name);
                ServoWrapper servoWrapper = new ServoWrapper(servo, servo.getPosition(), name);
                servos.add(servoWrapper);
            }
        }
    }
    public MotorWrapper getMotor (String name) {
        for (MotorWrapper motor : motors) {
            if (motor.getName().equals(name))
                return motor;
        }
        return null;
    }
    public ServoWrapper getServo (String name) {
        for (ServoWrapper servo : servos) {
            if (servo.getName().equals(name))
                return servo;
        }
        return null;
    }
}
