package org.firstinspires.ftc.teamcode.Automotons2425.Claw2425;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw2425 {
    /** The target open position of the servo */
    private double openPosition;
    /** The target closed position of the servo */
    private double closedPosition;
    /** A boolean storing if the claw is open */
    private boolean isOpen;
    /** The servo that control the claw
     * */
    private Servo claw;
    /** CONSTRUCTOR
     * Sets all instance variables */
    public Claw2425 (double openPos, double closedPos, Servo claw) {
        this.claw = claw;
        this.openPosition = openPos;
        this.closedPosition = closedPos;
        open();
    }
    /** Sets openPosition */
    private void setOpenPosition (double position) {
        openPosition = position;
    }
    /** Sets closedPosition */
    private void setClosedPosition (double position) {
        closedPosition = position;
    }
    /** It opens or closes the claw based on current state. */
    public void toggleClaw () {
        if (isOpen)
            close();
        else
            open();
    }
    /** Sets the claw’s position to openPosition */
    public void open() {
        claw.setPosition(openPosition);
        isOpen = true;
    }
    /** Sets the claw’s position to closedPosition */
    public void close() {
        claw.setPosition(closedPosition);
        isOpen = false;
    }

    /**
     * Incrementally recalibrates the positions based on user input
     * @param amount The amount open and close positions should be adjusted. Small amount recommended.
     */
    public void shiftPositions (double amount) {
        openPosition += amount;
        closedPosition += amount;
        if (isOpen)
            open();
        else
            close();
    }
}
