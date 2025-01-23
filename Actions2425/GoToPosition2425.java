package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;
public class GoToPosition2425 implements Action2425{
    private DriveTrain2425 driveTrain;
    private Position desiredPosition;
    private double tolerance;
    private PositionFinder2425 positionFinder;
    private double maxPower;
    private double maxPowerError;
    public GoToPosition2425 (DriveTrain2425 driveTrain, Position position, double tolerance, PositionFinder2425 positionFinder) {
        this.driveTrain = driveTrain;
        this.desiredPosition = position;
        this.tolerance = tolerance;
        this.positionFinder = positionFinder;
        // TODO: Fine tune/ test these values
        maxPower = 1;
        maxPowerError = 2000;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    @Override
    public boolean isComplete() {
        Position currentPosition = positionFinder.getPosition();
        double distance = currentPosition.distanceTo(desiredPosition);
        if (distance <= tolerance) {
            // TODO: make this work
            //driveTrain.translate(0,0);
        }
        return false;
    }

    /**
     * sets new tollerance value
     * @param newTolerance
     */
    public void setTolerance(double newTolerance){tolerance = newTolerance;}

    /**
     * returns tolerance
     * @return tolernace
     */
    public double getTolerance(){return  tolerance;}

    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        Position currentPosition = positionFinder.getPosition();
        double angleToTarget = currentPosition.angleTo(desiredPosition);
        SetOrientation2425 orientator = new SetOrientation2425(driveTrain, angleToTarget, Math.PI / 24, positionFinder);
        if (orientator.isComplete()) {
            double power = calculatePower(currentPosition.distanceTo(desiredPosition));
            //TODO: fix this
            //driveTrain.translate(0, power);
        } else {
            orientator.doAction();
        }
    }
    private double calculatePower (double error) {
        if (error >= maxPowerError) {
            return maxPower;
        } else if (error <= -1 * maxPowerError) {
            return -1 * maxPower;
        } else {
            double portionOfMaxDistance = error/maxPowerError;
            return maxPower * portionOfMaxDistance;
        }
    }
}
