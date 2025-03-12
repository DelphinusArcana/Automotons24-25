package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Claw2425;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SpinClaw2425 {
    private CRServo crServo;
    private double power;
    public SpinClaw2425 (CRServo servo, double power) {
        this.crServo = servo;
        this.power = power;
    }
    public static final SpinClaw2425 defaultSpinClaw(HardwareMap hardwareMap) {
        return new SpinClaw2425(hardwareMap.get(CRServo.class, "ClawCRServo"), 1);
    }
    public void stop () {
        crServo.setPower(0);
    }
    public void pull () {
        crServo.setPower(power);
    }
    public void push () {
        crServo.setPower(-1 * power);
    }
    public int getDirection() {return (int) Math.signum(power);}
    public double getPower() {return power;}
    public void setPower(double newPower) {
        power=newPower;
    }
    public void changePower(double powerChange) {
        power+=powerChange;
    }
}
