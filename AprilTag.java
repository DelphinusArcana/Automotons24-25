package org.firstinspires.ftc.Automotons;
/** Stores the data for an april tag in the world*/
public class AprilTag {
    /** The position of the april tag */
    private Position position;
    /** The april tag's id*/
    private int id;
    /** CONSTRUCTOR sets all the instance variables
     * @param id the id of this AprilTag
     * @param aPos this tag's position in the a direction
     * @param bPos this tag's position in the b direction
     * @param rotation the direction this tag is facing in*/
    public AprilTag(int id, double aPos, double bPos, double rotation) {
        position = new Position (aPos, bPos, rotation);
        this.id = id;
    }
    /** CONSTRUCTOR sets all the instance variables
     * @param id the id of this AprilTag
     * @param position this tag's position */
    public AprilTag (int id, Position position) {
        this.position = position;
        this.id = id;
    }
    /** Calculates the robot's position in the a-direction
     * @param x the tag's x-position with respect to the camera
     * @param y the tag's y-position with respect to the camera
     * @param yaw the tag's yaw (in radians) with respect to the camera
     * @return the robot's position in the a-direction */
    public double robotAPos (double x, double y, double yaw) {
        yaw -= position.facingDirection;
        return (x * Math.cos(-1 * yaw) - y * Math.sin(-1 * yaw) + position.aPos);
    }

    /** Calculates the robot's position in the b-direction
     * @param x the tag's x-position with respect to the camera
     * @param y the tag's y-position with respect to the camera
     * @param yaw the tag's yaw (in radians) with respect to the camera
     * @return the robot's position in the b-direction */
    // yaw in radians
    public double robotBPos (double x, double y, double yaw) {
        yaw -= position.facingDirection;
        return (x * Math.sin(-1 * yaw) + y * Math.cos(-1 * yaw) + position.bPos);
    }
    /** Calculates the direction (in radians) the robot is facing in
     * @param yaw the tag's yaw (in radians) with respect to the camera
     * @return the direction (in radians) the robot is facing in */
    public double robotDirection (double yaw) {
        return -1 * (yaw - position.facingDirection + Math.PI / 2);
    }
    /** Calculates the robot's position
     * @param x the tag's x-position with respect to the camera
     * @param y the tag's y-position with respect to the camera
     * @param yaw the tag's yaw (in radians) with respect to the camera
     * @return the robot's position */
    public Position robotPosition (double x, double y, double yaw) {
        double aPos = robotAPos(x, y, yaw);
        double bPos = robotBPos(x, y, yaw);
        double robotDirection = robotDirection(yaw);
        return new Position (aPos, bPos, robotDirection);
    }
    /** Returns the id of this tag
     * @return the id of this tag*/
    public int getId () {
        return id;
    }
}
