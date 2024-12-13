package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;
import org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425.PositionFinder2425;

public class setOrientation2425 {
    private DriveTrain2425 driveTrain;
    private double orientation;
    private double tolerance;
    private PositionFinder2425 positionFinder;
    private double maxPower;
    private double maxPowerError;
    public setOrientation2425 (DriveTrain2425 driveTrain, double orientation, double tolerance, PositionFinder2425 positionFinder) {
        this.driveTrain = driveTrain;
        this.orientation = orientation;
        this.tolerance = tolerance;
        this.positionFinder = positionFinder;
        // TODO: Fine tune/ test these values
        maxPower = 1;
        maxPowerError = Math.PI/2;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    public boolean isComplete() {
        Position currentPosition = positionFinder.getPosition();
        double diff = currentPosition.angleDiff(new Position(0, 0, orientation));
        if (Math.abs(diff) <= tolerance)
            return true;
        return false;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    public void doAction() {
        Position currentPosition = positionFinder.getPosition();
        double diff = currentPosition.angleDiff(new Position(0, 0, orientation));
        double power = calculatePower(diff);
        if (power < 0)
            driveTrain.rotate(0, power);
        else
            driveTrain.rotate(power,0);
    }
    private double calculatePower (double error) {
        if (error >= maxPowerError) {
            return -1 * maxPower;
        } else if (error <= -1 * maxPowerError) {
            return maxPower;
        } else {
            double portionOfMaxDistance = error/maxPowerError;
            return -1 * maxPower * portionOfMaxDistance;
        }
    }
}
