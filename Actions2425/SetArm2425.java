package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

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
    public boolean isComplete() {
        if (Math.abs(arm.getCurrentPosition() - position) <= tolerance) {
            return true;
        }
        return false;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction() {
        arm.setTargetPosition(position);
        arm.powerArm();
    }
}
