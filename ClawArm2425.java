package org.firstinspires.ftc.teamcode.Automotons2425;
import com.qualcomm.robotcore.hardware.DcMotor;
//I'm wondering whether we'll need to be applying constant power to the motor just to keep the arm up. If so, it might look closer to the lift kit. If not, this will probably just use the DcMotor.setPosition() function.
//TODO: make this
public class ClawArm2425 {
    /* The height at which the arm is completely lowered
     */
    private int zeroPosition;
    /* The height at which the arm is at its maximum height
     */
    private int uprightPosition;
    /* The difference between zeroPosition and uprightPosition. Constant no matter motor start pos
     */
    private final int rangeOfMotionDistance;
    /* Claw Arm Motor
     */
    private DcMotor motor;
    /* The height at which the arm should be
     */
    private int targetPosition;
    /** CONSTRUCTOR
     Sets all instance variables
     */
    public ClawArm2425 (DcMotor motor)
    /* Mutator method to set the arm’s target position
     */
    private void setTargetPostion (int position)
    /* Gives power to the motor to maintain or increase arm power.
     */
    private void powerArm ()
}
