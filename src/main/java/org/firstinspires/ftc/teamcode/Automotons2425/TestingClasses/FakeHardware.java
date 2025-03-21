package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.TestingClasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425.FakeTelemetry;

/** Holds non-functional implementations of DcMotor and Servo*/
public class FakeHardware {
    /** a non-functional implementation of DcMotor*/
    public static final DcMotor fakeMotor = new DcMotor() {
        @Override
        public MotorConfigurationType getMotorType() {
            return null;
        }

        @Override
        public void setMotorType(MotorConfigurationType motorType) {

        }

        @Override
        public DcMotorController getController() {
            return null;
        }

        @Override
        public int getPortNumber() {
            return 0;
        }

        @Override
        public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {

        }

        @Override
        public ZeroPowerBehavior getZeroPowerBehavior() {
            return null;
        }

        @Override
        public void setPowerFloat() {

        }

        @Override
        public boolean getPowerFloat() {
            return false;
        }

        @Override
        public void setTargetPosition(int position) {

        }

        @Override
        public int getTargetPosition() {
            return 0;
        }

        @Override
        public boolean isBusy() {
            return false;
        }

        @Override
        public int getCurrentPosition() {
            return 0;
        }

        @Override
        public void setMode(RunMode mode) {

        }

        @Override
        public RunMode getMode() {
            return null;
        }

        @Override
        public void setDirection(Direction direction) {

        }

        @Override
        public Direction getDirection() {
            return null;
        }

        @Override
        public void setPower(double power) {

        }

        @Override
        public double getPower() {
            return 0;
        }

        @Override
        public Manufacturer getManufacturer() {
            return null;
        }

        @Override
        public String getDeviceName() {
            return "";
        }

        @Override
        public String getConnectionInfo() {
            return "";
        }

        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public void resetDeviceConfigurationForOpMode() {

        }

        @Override
        public void close() {

        }
    };
    /** a non-functional implementation of Servo */
    public static final Servo fakeServo = new Servo() {
        @Override
        public ServoController getController() {
            return null;
        }

        @Override
        public int getPortNumber() {
            return 0;
        }

        @Override
        public void setDirection(Direction direction) {

        }

        @Override
        public Direction getDirection() {
            return null;
        }

        @Override
        public void setPosition(double position) {

        }

        @Override
        public double getPosition() {
            return 0;
        }

        @Override
        public void scaleRange(double min, double max) {

        }

        @Override
        public Manufacturer getManufacturer() {
            return null;
        }

        @Override
        public String getDeviceName() {
            return "";
        }

        @Override
        public String getConnectionInfo() {
            return "";
        }

        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public void resetDeviceConfigurationForOpMode() {

        }

        @Override
        public void close() {

        }
    };
    public static final Telemetry fakeTelemetry = FakeTelemetry.fake;
}
