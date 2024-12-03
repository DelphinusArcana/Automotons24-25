package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.hardware.DcMotor;
//I'm wondering whether we'll need to be applying constant power to the motor just to keep the arm up. If so, it might look closer to the lift kit. If not, this will probably just use the DcMotor.setPosition() function.
//TODO: make this
public class ClawArm2425 {
    /**
     * The height at which the arm is completely lowered
     */
    private int zeroPosition;
    /** The height at which the arm is at its maximum height
     */
    private int uprightPosition;
    /**
     * The difference between zeroPosition and uprightPosition. Constant no matter motor start pos
     */
    private int rangeOfMotionDistance;
    /**
     * Claw Arm Motor
     */
    private final DcMotor motor;
    /**
     * The height at which the arm should be
     */
    private int targetPosition;
    /** The distance from target at which to send the maximum power to the motors */
    private int maxPowerError;
    /** The maximum power to send to the motors */
    private double maxPower;
    /**
     * CONSTRUCTOR
     Sets all instance variables
     */
    public ClawArm2425 (DcMotor motor) {
        this.motor = motor;
        zeroPosition = 0;
        uprightPosition = 0;
        targetPosition = 0;
        maxPowerError = 0;
        maxPower = 0;
    }
    /**
     * Mutator method to set the arm’s target position
     */
    public void setTargetPosition (int position) {
        targetPosition = position;
    }
    /**
     * Changes the target height by specified amount
     * Allows for incremental changes to arm height
     * @param increase the amount the target height should change */
    public void changeTargetPosition (int increase) {
        targetPosition += increase;
    }
    /**
     * Gives power to the motor to maintain or increase arm power.
     */
    public void powerArm () {
        int error = motor.getCurrentPosition() - targetPosition;
        double power = 0;
        if (error >= maxPowerError) {
            power = maxPower;
        } else if (error <= -1 * maxPowerError) {
            power =  -1 * maxPower;
        } else {
            double portionOfMaxDistance = error/(double) maxPowerError;
            power = maxPower * portionOfMaxDistance;
        }
        motor.setPower(power);
    }

    /**
     * Mutator method for upright position (fully upright motor position value)
     * @param uprightPosition new upright position
     */
    public void setUprightPosition(int uprightPosition) {
        this.uprightPosition = uprightPosition;
    }

    /**
     * Mutator method for zero position (bottom motor position value)
     * @param zeroPosition new zero position
     */
    public void setZeroPosition(int zeroPosition) {
        this.zeroPosition = zeroPosition;
    }

    /**
     * Mutator method for max power (maximum power allowed to be sent to the motor)
     * @param maxPower new maximum power
     */
    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    /**
     * Mutator method for max power error (the error associated with the maximum power value)
     * @param maxPowerError new max error value
     */
    public void setMaxPowerError(int maxPowerError) {
        this.maxPowerError = maxPowerError;
    }

    /**
     * Mutator method for range of motion distance (difference in position between zero and max position)
     * @param distance The distance between min and max positions
     */
    public void setRangeOfMotionDistance (int distance) {
        rangeOfMotionDistance = distance;
    }
}
