package org.firstinspires.ftc.teamcode.Automotons2425.Actions;

interface Action2425 {
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     * May stop relevant motors when the action is complete
     */
    public boolean isComplete();
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction();
}