package org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;


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
    private double targetPosition;
    /** The distance from target at which to send the maximum power to the motors */
    private int maxPowerError;
    /** The maximum power to send to the motors */
    private double maxPower;
   // private double timeSinceInput;
    private boolean direction;
   // private double updateTimeMoveLimitCoeficient;
    int previousMili;
    private int prevMotorPosition;
    private ElapsedTime elapsedTime;
    /**
     * CONSTRUCTOR
     Sets all instance variables
     */
    public ClawArm2425 (DcMotor motor) {
        this.motor = motor;
        zeroPosition = motor.getCurrentPosition();
        //motor.setTargetPosition(zeroPosition);
        //TODO: make this our code not FTC's (FTC's is bad)
        //motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        uprightPosition = -10000000;
        targetPosition = zeroPosition;
        prevMotorPosition = zeroPosition;
        maxPowerError = 5;
        maxPower = 0.5;
        previousMili = 0;
        elapsedTime = new ElapsedTime();
        //todo find this
        //updateTimeMoveLimitCoeficient = 10;
        direction = true;
        updateDirection();
    }
    /**
     * Mutator method to set the arm’s target position
     */
    public void setTargetPosition (double position) {
        /*
        double changeInTargetPosition = position-targetPosition;

        timeSinceInput -= Math.abs(changeInTargetPosition)*updateTimeMoveLimitCoeficient;
        */

        targetPosition = position;
        /*if (targetPosition < uprightPosition){
            targetPosition = uprightPosition;
        }
        else if (targetPosition > zeroPosition){
            targetPosition = zeroPosition;
        }*/
    }
    public double getTargetPosition () {
        return targetPosition;
    }
    public double getCurrentPosition () {
        return motor.getCurrentPosition();
    }
    /**
     * Changes the target height by specified amount
     * Allows for incremental changes to arm height
     * @param increase the amount the target height should change */
    public void changeTargetPosition (double increase) {
        targetPosition += increase;
    }
    /**
     * Gives power to the motor to maintain or increase arm power.
     */
    public void powerArm (Telemetry telemetry) {
        //motor.setPower(maxPower);
        //int elapsedMili = (int) elapsedTime.milliseconds()-previousMili;
        //previousMili = (int) elapsedTime.milliseconds();
        //motor.setTargetPosition((int) targetPosition);
        int currentPosition = motor.getCurrentPosition();
        if (Math.abs(currentPosition - prevMotorPosition) > 3) {
            int offset = currentPosition - prevMotorPosition;
            targetPosition += offset;
        }
        prevMotorPosition = currentPosition;
        /*
        if (timeSinceInput>0){
            int offset = currentPosition - prevMotorPosition;
            targetPosition += offset;
        }
        else{
            timeSinceInput += elapsedMili;
        }

         */


        double error = targetPosition - motor.getCurrentPosition();
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
        telemetry.addData("target position", targetPosition);
        telemetry.addData("power",power);
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

    /**
     * Updates the motor direction based off of instance direction
     */
    public void updateDirection () {
        if (direction) {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        } else
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    /** toggles the direction of the motor */
    public void switchDirection (){
        direction = !direction;
        updateDirection();
    }
    /** sets the direction of the motor */
    public void setDirection (boolean direction) {
        this.direction = direction;
        updateDirection();
    }
    public static ClawArm2425 defaultArm (HardwareMap hardwareMap) {
        return new ClawArm2425(hardwareMap.get(DcMotor.class, "armMotor"));
    }
}
