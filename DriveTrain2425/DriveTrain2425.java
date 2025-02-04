package org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    private static final DcMotorSimple.Direction forward = DcMotorSimple.Direction.FORWARD;
    private static final DcMotorSimple.Direction reverse = DcMotorSimple.Direction.REVERSE;

    // TODO: find these two numbers
    /** The approximate number of inches the robot moves for every unit of motor position */
    // TODO: figure out if this works the same for left-right and front-back
    public static final double INCHES_PER_MOTOR_POS = 1.0 / 25.0;
    /** The approximate angle (in radians because degrees are fake) the robot moves for every unit of motor position*/
    public static final double RADIANS_PER_MOTOR_POS = 0.00305127491607; //1;//Math.PI / 1000.0;
    public static final double minTranslatePower = 0.25;
    /** CONSTRUCTOR sets all instance variables
     * @param wheels the motors that control the wheels
     * */
    public DriveTrain2425(DcMotor[] wheels, boolean[] directions) {
        this.wheels = wheels;
        this.directions = directions;
        updateDirections();
        wheelsPastPosition = new double[wheels.length];
        for (int i = 0; i < wheels.length; i++) {
            wheelsPastPosition[i] = wheels[i].getCurrentPosition();
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
        dist[0] = INCHES_PER_MOTOR_POS*0.25*( // left-right distance with respect to robot
                + (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                - (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                + (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                - (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );
        dist[1] = INCHES_PER_MOTOR_POS*0.25*( // front-back distance with respect to robot
                  (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                + (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                + (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                + (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );

    // Determine how far the robot has turned (in radians because degrees are fake) since the last time updatePosition() was called
        double rotation = 0;
        rotation = (
                + (wheelsPastPosition[0] - wheelsCurrentPosition[0])
                - (wheelsPastPosition[1] - wheelsCurrentPosition[1])
                + (wheelsPastPosition[2] - wheelsCurrentPosition[2])
                - (wheelsPastPosition[3] - wheelsCurrentPosition[3])
        );
        for (int i = 0; i < wheels.length; i++) {
            wheelsPastPosition[i] =  wheelsCurrentPosition[i];
        }
        rotation *= 0.25; // average of the distances
        rotation *= RADIANS_PER_MOTOR_POS;
        return new Position(dist[0], dist[1], rotation);
    }
    //TODO: this is a pretty dangerous structure
    public  double[] getWheelPosition() {return wheelsPastPosition;}
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
    /*public void translate (double xVal, double yVal) {
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
    }*/
    /** like translate() but with feedback */
    /*public double translate (double xVal, double yVal, boolean opp) {
        double mainPower = calcTranslatePower(Math.hypot(xVal, yVal));
        if (mainPower > 1) mainPower = 1;
        //if (mainPower < -1) mainPower = -1;
        if (xVal == 0.0 && yVal == 0.0) {
            for (DcMotor wheel : wheels)
                wheel.setPower(0.0);
        } else if (xVal >= 0.0 && yVal >= 0.0) {
            wheels[1].setPower(mainPower);
            wheels[3].setPower(mainPower);
            double oppPower = yVal - xVal;
            wheels[0].setPower(oppPower);
            wheels[2].setPower(oppPower);
            if (opp)
                return oppPower;
        } else if (xVal <= 0.0 && yVal >= 0.0) {
            wheels[0].setPower(mainPower);
            wheels[2].setPower(mainPower);
            double oppPower = yVal + xVal;
            wheels[1].setPower(oppPower);
            wheels[3].setPower(oppPower);
            if (opp)
                return oppPower;
        } else if (xVal <= 0.0 && yVal <= 0.0) {
            wheels[1].setPower(-1 * mainPower);
            wheels[3].setPower(-1 * mainPower);
            double oppPower = yVal - xVal;
            wheels[0].setPower(oppPower);
            wheels[2].setPower(oppPower);
            if (opp)
                return oppPower;
        } else {
            wheels[0].setPower(-1 * mainPower);
            wheels[2].setPower(-1 * mainPower);
            double oppPower = yVal + xVal;
            wheels[1].setPower(oppPower);
            wheels[3].setPower(oppPower);
            if (opp)
                return oppPower;
        }
        return mainPower;
    }*/
    /* Emmett's translate idea thing*/
    public void translate (double xVal, double yVal, Telemetry telemetry) {
        for (DcMotor motor : wheels) {
            motor.setPower(yVal);
        }
        telemetry.addData("YVal",yVal);
    }
    public void translate (boolean doEmmettsThing ,double xVal, double yVal, Telemetry telemetry) {
        double[] powers = {0,0,0,0};

        //scale down xVal and yVal to make sure that they add to 1
        /*if (Math.abs(xVal)+Math.abs(yVal) > 1) {
            double scaleFactor = 1.0 / (Math.abs(xVal) + Math.abs(yVal));
            xVal *= scaleFactor;
            yVal *= scaleFactor;
        }*/
        /*
        //implimented Asher's thing
        if (Math.abs(xVal)+Math.abs(yVal) < minTranslatePower){
            double scaleFactor = minTranslatePower / (Math.abs(xVal) + Math.abs(yVal));
            xVal *= scaleFactor;
            yVal *= scaleFactor;
        }*/

        //x translation
        powers[0] = powers[0] - xVal;
        powers[1] = powers[1] + xVal;
        powers[2] = powers[2] - xVal;
        powers[3] = powers[3] + xVal;

        //y translation
        powers[0] = powers[0] + yVal;
        powers[1] = powers[1] + yVal;
        powers[2] = powers[2] + yVal;
        powers[3] = powers[3] + yVal;

        //set powers
        wheels[0].setPower(powers[0]);
        wheels[1].setPower(powers[1]);
        wheels[2].setPower(powers[2]);
        wheels[3].setPower(powers[3]);

    }
    public void translateAndRotate (double lDepth, double rDepth, double xVal, double yVal, Telemetry telemetry) {
        double[] powers = {0,0,0,0};

        //scale down xVal and yVal to make sure that they add to 1
        /*if (Math.abs(xVal)+Math.abs(yVal) > 1) {
            double scaleFactor = 1.0 / (Math.abs(xVal) + Math.abs(yVal));
            xVal *= scaleFactor;
            yVal *= scaleFactor;
        }*/
        /*
        //implimented Asher's thing
        if (Math.abs(xVal)+Math.abs(yVal) < minTranslatePower){
            double scaleFactor = minTranslatePower / (Math.abs(xVal) + Math.abs(yVal));
            xVal *= scaleFactor;
            yVal *= scaleFactor;
        }*/

        //x translation
        powers[0] = powers[0] - xVal;
        powers[1] = powers[1] + xVal;
        powers[2] = powers[2] - xVal;
        powers[3] = powers[3] + xVal;

        //y translation
        powers[0] = powers[0] + yVal;
        powers[1] = powers[1] + yVal;
        powers[2] = powers[2] + yVal;
        powers[3] = powers[3] + yVal;

        double lPower = lDepth - rDepth;
        //rotation
        powers[0]+=-1 * lPower;
        powers[1]+=-1 * lPower;
        powers[2]+=lPower;
        powers[3]+=lPower;

        double biggestPower = 0;
        for(int i = 0; i<4; i++){
            if(Math.abs(powers[i])>biggestPower){
                biggestPower = Math.abs(powers[i]);
            }
        }
        if (biggestPower>1) {
            double powerScaleFactor = 1 / biggestPower;
            for (int i = 0; i < 4; i++) {
                powers[i] = powers[i] / powerScaleFactor;
            }
        }

        //set powers
        wheels[0].setPower(powers[0]);
        wheels[1].setPower(powers[1]);
        wheels[2].setPower(powers[2]);
        wheels[3].setPower(powers[3]);

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
    /** gets the power sent to a motor as specified by the index */
    public double getMotorPower (int index) {
        return wheels[index].getPower();
    }
    public double calcTranslatePower (double rawPower) {
        if (rawPower > 1)
            rawPower = 1;
        return rawPower;
        //return rawPower * (1-minTranslatePower) + Math.signum(rawPower) * minTranslatePower;
    }
    public static DriveTrain2425 defaultDrive(HardwareMap hardwareMap) {
        return new DriveTrain2425(new DcMotor[]{
                hardwareMap.get(DcMotor.class,"leftFrontDrive"),
                hardwareMap.get(DcMotor.class,"leftRearDrive"),
                hardwareMap.get(DcMotor.class,"rightRearDrive"),
                hardwareMap.get(DcMotor.class,"rightFrontDrive")
        },
                new boolean[] {true,true,false,false}
        );
    }
}
