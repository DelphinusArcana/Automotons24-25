package org.firstinspires.ftc.teamcode.Automotons2425.Actions;

import org.firstinspires.ftc.teamcode.Automotons2425.Lift2425;

public class SetLift2425 implements Action2425{
    private Lift2425 lift;
    private double height;
    private double tolerance;
    public SetLift2425 (Lift2425 lift, double height, double tolerance) {
        this.lift = lift;
        this.height = height;
        this.tolerance = tolerance;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    public boolean isComplete() {
        if (Math.abs(lift.getAverageHeight() - height) <= tolerance) {
            return true;
        }
        return false;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction() {
        lift.setTargetHeight(height);
        lift.powerMotors();
    }
}
