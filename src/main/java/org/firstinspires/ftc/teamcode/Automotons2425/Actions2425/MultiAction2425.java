package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import java.util.ArrayList;

public class MultiAction2425 implements Action2425 {
    ArrayList<Action2425> actions;
    public MultiAction2425(ArrayList<Action2425> actions) {
        this.actions = actions;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    @Override
    public boolean isComplete() {
        for (Action2425 action : actions) {
            if (!action.isComplete()) {
                return false;
            }
        }
        return true;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        for (Action2425 action : actions) {
            action.doAction();
        }
    }
}
