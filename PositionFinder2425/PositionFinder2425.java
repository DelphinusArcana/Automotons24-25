package org.firstinspires.ftc.teamcode.Automotons2425.PositionFinder2425;
import org.firstinspires.ftc.teamcode.Automotons2425.DriveTrain2425.DriveTrain2425;
import org.firstinspires.ftc.teamcode.Automotons2425.Position;

/* Based entirely on */
public class PositionFinder2425 {
    protected DriveTrain2425 driveTrain;
    protected Position currentPosition;

    public PositionFinder2425 (DriveTrain2425 driveTrain, Position position) {
        this.driveTrain = driveTrain;
        currentPosition = position;
    }
    /** Returns the current position of the robot*/
    public Position getPosition () {
        return currentPosition;
    }
    /**
     * Sets the current running position to a specified position
     * @param position The new position
     */
    public void setPosition (Position position) {
        currentPosition = new Position(position);
    }
    /**
     * Updates the position by adding the current running position to the parameters
     * @param xDistance The distance in the x direction the drive train has moved since last updating
     * @param yDistance The distance in the y direction the drive train has moved since last updating
     * @param angle The angle (in radians) the robot has rotated since last updating
     */
    protected void adjustPositionAbsoluteSpace (double xDistance, double yDistance, double angle) {
        Position newPosition = new Position(
                currentPosition.aPos+xDistance,
                currentPosition.bPos+yDistance,
                currentPosition.facingDirection+angle
        );
        currentPosition = new Position(newPosition);
    }
    /**
     * Updates the position by adding the current running position to the parameters
     * multiplies xDistance and yDistance by current angle rotation matrix to transform into absolute space
     * @param xDistance The distance in the x direction the drive train has moved since last updating
     * @param yDistance The distance in the y direction the drive train has moved since last updating
     * @param angle The angle (in radians) the robot has rotated since last updating
     */
    protected void adjustPositionRobotSpace (double xDistance, double yDistance, double angle) {
        Position newPosition = new Position(
                currentPosition.aPos+xDistance*Math.cos(currentPosition.facingDirection) - yDistance * Math.sin(currentPosition.facingDirection),
                currentPosition.bPos+xDistance*Math.sin(currentPosition.facingDirection) + yDistance * Math.cos(currentPosition.facingDirection),
                currentPosition.facingDirection+angle
        );
        currentPosition = new Position(newPosition);
    }

    public void updatePosition () {
        Position changeInPosition = driveTrain.positionChange();
        adjustPositionRobotSpace(changeInPosition.aPos, changeInPosition.bPos, changeInPosition.facingDirection);
    }
}
