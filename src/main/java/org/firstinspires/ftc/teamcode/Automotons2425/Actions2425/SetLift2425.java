package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425.Lift2425;

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
    @Override
    public boolean isComplete() {
        return Math.abs(lift.getAverageHeight() - height) <= tolerance;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        lift.setTargetHeight(height);
        lift.powerMotors();
    }
}
