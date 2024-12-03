package org.firstinspires.ftc.teamcode.Automotons2425;

interface Action2425 {
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    public boolean isComplete();
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction();
}
