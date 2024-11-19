package org.firstinspires.ftc.teamcode.Automotons2425;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    /** The target open position of the servo
     */
    private double openPosition;
    /** The target closed position of the servo
     */
    private double closedPosition;
    /** A boolean storing if the claw is open
     */
    private boolean isOpen;

    private final Servo claw;

    /** CONSTRUCTOR
     * Sets the open and closed positions of the servo
     */
    public Claw (Servo claw, double openPosition, double closedPosition) {
        this.claw = claw;
        this.openPosition = openPosition;
        this.closedPosition = closedPosition;
        claw.setPosition(openPosition);
        isOpen = true;
    }
    /** Sets openPosition
     */
    private void setOpenPosition (double position) {
        openPosition = position;
    }
    /* Sets closedPosition
     */
    private void setClosedPosition (double position) {
        closedPosition = position;
    }
    /* It opens or closes the claw based on current state.
     */
    public void toggleClaw () {
        if (isOpen) {
            claw.setPosition(closedPosition);
        } else {
            claw.setPosition(openPosition);
        }
        isOpen = !isOpen;
    }

}