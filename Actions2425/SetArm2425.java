package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;

public class SetArm2425 implements Action2425 {
    private ClawArm2425 arm;
    private double position;
    private double tolerance;
    public SetArm2425 (ClawArm2425 arm, double position, double tolerance) {
        this.arm = arm;
        this.position = position;
        this.tolerance = tolerance;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    @Override
    public boolean isComplete() {
        return Math.abs(arm.getCurrentPosition() - position) <= tolerance;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        arm.setTargetPosition(position);
        arm.powerArm(FakeTelemetry.fake);
    }
    public double getDesiredPosition() {return position;}
}
