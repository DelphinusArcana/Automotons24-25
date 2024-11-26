package org.firstinspires.ftc.teamcode.Automotons2425;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveTrain2425 {
    /** A 1x4 Array assigned to 4 different wheels.
     * Left Front is index 0
     * Left Rear is index 1
     * Right Rear is index 2
     * Right Front is index 3
     */
    private DcMotor[] wheels;
    /** CONSTRUCTOR sets all instance variables
     * @param wheels the motors that control the wheels
     * */
    public DriveTrain2425(DcMotor[] wheels) {
        this.wheels = wheels;
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
}
