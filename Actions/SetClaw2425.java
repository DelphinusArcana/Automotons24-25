package org.firstinspires.ftc.teamcode.Automotons2425.Actions;

import org.firstinspires.ftc.teamcode.Automotons2425.Claw2425;

public class SetClaw2425 {
    private Claw2425 claw;
    private boolean setOpen;

    /**
     * Constructor for SetClaw class
     * @param claw The claw instance
     * @param setOpen If the claw should be open
     */
    public SetClaw2425 (Claw2425 claw, boolean setOpen) {
        this.claw = claw;
        this.setOpen = setOpen;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    public boolean isComplete() {
        return true;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction() {
        if (setOpen)
            claw.open();
        else
            claw.close();
    }
}
