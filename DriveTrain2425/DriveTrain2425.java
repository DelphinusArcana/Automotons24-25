package org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;


public class DriveTrain2425 {
    /** A 1x4 Array assigned to 4 different wheels.
     * Left Front is index 0
     * Left Rear is index 1
     * Right Rear is index 2
     * Right Front is index 3
     */
    private DcMotor[] wheels;
    /** The directions of the motors
     * true is forward. */
    private boolean[] directions;
    private double[] wheelsPastPosition;
    private double[] wheelsPastPosition;

    private static final DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;
    /** CONSTRUCTOR sets all instance variables
     * @param wheels the motors that control the wheels
     * */
    /** The approximate number of inches the robot moves for every unit of motor position */
    public static final double INCHES_PER_MOTOR_POS = 1.0 / 25.0;
    /** The approximate angle (in radians because degrees are fake) the robot moves for every unit of motor position*/
    public static final double RADIANS_PER_MOTOR_POS = Math.PI / 1000.0;


    public DriveTrain2425(DcMotor[] wheels, boolean[] directions) {
        this.wheels = wheels;
        this.directions = directions;
        updateDirections();
        for (int i = 0; i < wheels.length; i++) {
            wheelsPastPosition[i] = 0;
            wheelsPastPosition[i] = 0;
        }
    }
    /** Determines how far forward the robot has moved (in inches) since the last time updatePosition() was called
     * @return the forward distance the robot has moved (in inches) since the last time updatePosition() was called */

    public Position positionChange () {
        double[] wheelsCurrentPosition = {0,0,0,0};
        for (int i = 0; i < wheels.length; i++) {
            wheelsCurrentPosition[i] = wheels[i].getCurrentPosition();
        }
        double[] dist = {0,0};
        dist[0] = INCHES_PER_MOTOR_POS*0.25*(
                  (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                - (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                - (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                + (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );
        dist[1] = INCHES_PER_MOTOR_POS*0.25*(
                  (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                + (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                + (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                + (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );

    /** Determines how far the robot has turned (in radians because degrees are fake) since the last time updatePosition() was called
     * @return the angle the robot has turned (in radians because degrees are fake) since the last time updatePosition() was called */
        double rotation = 0;
        rotation = (
                - (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                - (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                + (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                + (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );
        for (int i = 0; i < wheels.length; i++) {
            wheelsPastPosition[i] =  wheelsCurrentPosition[i];
        }
        rotation *= 0.25; // average of the distances
        rotation *= RADIANS_PER_MOTOR_POS;
        return new Position(dist[0], dist[1], rotation);
    }
    /** Sets the directions of each motor to what directions says it should be */
    public void updateDirections() {
        for (int i = 0; i < wheels.length; i++) {
            if (directions[i])
                wheels[i].setDirection(forward);
            else
                wheels[i].setDirection(reverse);
        }
    }
    /** Moves the robot on the x/y axes.
     * @param xVal the amount to move in the x direction (left and right)
     * @param yVal the amount to move in the y direction (forward and backward)
     */
    public void translate (double xVal, double yVal) {
        double totalPower = Math.hypot(xVal, yVal);
        if (totalPower > 1) totalPower = 1;
        //if (totalPower < -1) totalPower = -1;
        if (xVal == 0.0 && yVal == 0.0) {
            for (DcMotor wheel : wheels)
                wheel.setPower(0.0);
        } else if (xVal >= 0.0 && yVal >= 0.0) {
            wheels[1].setPower(totalPower);
            wheels[3].setPower(totalPower);
            double oppPower = yVal - xVal;
            wheels[0].setPower(oppPower);
            wheels[2].setPower(oppPower);
        } else if (xVal <= 0.0 && yVal >= 0.0) {
            wheels[0].setPower(totalPower);
            wheels[2].setPower(totalPower);
            double oppPower = yVal + xVal;
            wheels[1].setPower(oppPower);
            wheels[3].setPower(oppPower);
        } else if (xVal <= 0.0 && yVal <= 0.0) {
            wheels[1].setPower(-1 * totalPower);
            wheels[3].setPower(-1 * totalPower);
            double oppPower = yVal - xVal;
            wheels[0].setPower(oppPower);
            wheels[2].setPower(oppPower);
        } else {
            wheels[0].setPower(-1 * totalPower);
            wheels[2].setPower(-1 * totalPower);
            double oppPower = yVal + xVal;
            wheels[1].setPower(oppPower);
            wheels[3].setPower(oppPower);
        }
    }
    /** Rotates the robot using the analog triggers on the controller, more depth means faster rotations
     *@param lDepth amount trigger is pressed corresponding to speed of leftward rotation
     *@param rDepth amount trigger is pressed corresponding to speed of rightward rotation
     */
    public void rotate(double lDepth, double rDepth) {
        double lPower = lDepth - rDepth;
        wheels[0].setPower(-1 * lPower);
        wheels[1].setPower(-1 * lPower);
        wheels[2].setPower(lPower);
        wheels[3].setPower(lPower);
    }
    public boolean getDirection(int motorIndex){
        return directions[motorIndex];
    }
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
}
