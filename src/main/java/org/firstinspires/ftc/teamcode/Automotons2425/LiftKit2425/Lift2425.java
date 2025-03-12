package org.firstinspires.ftc.teamcode.Automotons2425.src.main.java.org.firstinspires.ftc.teamcode.Automotons2425.LiftKit2425;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** PLEASE NOTE: if you are using this class, you MUST call powerMotors() each time through your code (such as the while(opModeIsActive()) loop in a TeleOp)*/
public class Lift2425 {
    /** The height at which the lift should be */
    private double targetHeight;
    /** Array of 4 start positions used to calculate the currentHeight. 0 is front left motor, counting counter clockwise from there*/
    private int[] startPositions;
    /** Array of 4 motors that control the lift kit
     * Indexes: 0 is front left motor, counting counterclockwise */
    private DcMotor[] motors;
    /** The directions of the motors
     * true is forward. */
    private boolean[] directions;
    private static final DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;
    // For an explanation on the below two variables, see this desmos: https://www.desmos.com/calculator/lnqlulrxwq
    /** The distance from target at which to send the maximum power to the motors */
    private int maxPowerError;
    /** The maximum power to send to the motors */
    private double maxPower;
    /** CONSTRUCTOR
     * Initializes motors
     * @param motors An array of motors for each wheel length 4. Indexes: 0 is front left motor, counting counterclockwise
     * @param directions An array of boolean directions. True is forward.
     */
    public Lift2425 (DcMotor[] motors, boolean[] directions) {
        this.motors = motors;
        this.directions = directions;
        updateDirections();
        targetHeight = 0;
        startPositions = new int[motors.length];
        for (int i = 0; i < motors.length; i++) {
            startPositions[i] = motors[i].getCurrentPosition();
        }
        //TODO: get these
        maxPowerError = 40;
        maxPower = 0.7;
    }
    /** Sets the directions of each motor to what directions says it should be */
    public void updateDirections() {
        for (int i = 0; i < motors.length; i++) {
            if (directions[i])
                motors[i].setDirection(forward);
            else
                motors[i].setDirection(reverse);
        }
    }
    /** Sets the target height
     * param height the lift should be
     */
    public void setTargetHeight (double height) {
        targetHeight = height;
    }
    /** Incrementally sets the distance for the lift to go amount
     * @param increase the amount the target height should change */
    public void changeTargetHeight (double increase) {
        targetHeight += increase;
    }
    public double getTargetHeight() {
        return targetHeight;
    }
    /** Powers the motors based on current power and updates the current height */
    public void powerMotors () {
        for (int i = 0; i < motors.length; i++) {
            motors[i].setPower(calculatePower(i));
        }
    }
    /** Calculates the power of a motor based on the difference between current position and target height */
    private double calculatePower (int motorIndex) {
        int error = getCurrentOffset(motorIndex);
        if (error >= maxPowerError) {
            return maxPower;
        } else if (error <= -1 * maxPowerError) {
            return -1 * maxPower;
        } else {
            double portionOfMaxDistance = error/(double) maxPowerError;
            return maxPower * portionOfMaxDistance;
        }
    }
    /** Calculates the difference between the current height and the target height */
    private int getCurrentOffset (int motorIndex) {
        return (int) targetHeight - getCurrentPosition(motorIndex);
    }
    /** Calculates the current position of a motor relative to its start position */
    public int getCurrentPosition (int motorIndex) {
        return motors[motorIndex].getCurrentPosition() - startPositions[motorIndex];
    }
    /** Powers the motors such that they are in the same position */
    public void syncMotors () {
        setTargetHeight(getAverageHeight());
    }
    /** Sets zero position of a motor */
    public void updateMotorStartPosition (int motorIndex, int newStartposition) {
        startPositions[motorIndex] = newStartposition;
    }
    /** Sets the start positions of each motor to its current position. Sets the target height to the average current height. */
    public void zeroMotors() {
        for (int i = 0; i < motors.length; i++) {
            startPositions[i] = motors[i].getCurrentPosition();
        }
        setTargetHeight(getAverageHeight());
    }
    /** Average distance from the motors’ starting position and current position */
    public int getAverageHeight () {
        int sum = 0;
        for (int i = 0; i < motors.length; i++) {
            sum += getCurrentPosition(i);
        }
        return sum/motors.length;
    }
    /** Sets the maximum power to send to the motors */
    public void setMaxPower (double power) {
        maxPower = power;
    }
    /** Changes the maximum power to send to the motors by a given amount */
    public void changeMaxPower (double change) {
        maxPower += change;
    }
    public double getMaxPower () {return maxPower;}
    /** Sets the distance from the target at which the motors get the maximum power */
    public void setMaxPowerError (int distance) {
        maxPowerError = distance;
    }
    /** Changes the distance from the target at which the motors get the maximum power  */
    public void changeMaxPowerError (int change) {
        maxPowerError += change;
    }
    public double getMaxPowerError () {return maxPowerError;}
    public boolean getDirection(int motorIndex){return directions[motorIndex];}
    /** toggles the direction of one motor */
    public void switchDirection(int motorIndex){
        directions[motorIndex] = !directions[motorIndex];
        updateDirections();
    }
    /** sets the direction of one motor */
    public void setDirection (int motorIndex, boolean direction) {
        directions[motorIndex] = direction;
        updateDirections();
    }
    /** Gets the start position of a specified motor*/
    public double getStartPosition (int index) {
        return startPositions[index];
    }
    /** Changes the start position of a specified motor*/
    public void increaseStartPos (int index, int increase) {
        startPositions[index] += increase;
    }
    public static Lift2425 defaultLift(HardwareMap hardwareMap) {
        return new Lift2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class, "leftLift"),
                hardwareMap.get(DcMotor.class, "rightLift")
        },
                new boolean[] {false,false}
        );
    }
}