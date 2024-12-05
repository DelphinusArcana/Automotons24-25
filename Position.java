package org.firstinspires.ftc.teamcode.Automotons2425;

import java.util.ArrayList;

/** A position of a point in the world */
public class Position {
    /** The position of the point along the a-axis (which is like the x-axis in cartesian coordinates, but x,y, and z were taken */
    public double aPos;
    /** The position of the point along the b-axis (which is like the y-axis in cartesian coordinates, but x,y, and z were taken */
    public double bPos;
    /** The direction (in radians) the point is facing. 0 means parallel to the a-axis in the positive direction, pi/2 means along the y-axis in the positive direction, etc*/
    public double facingDirection;
    /** CONSTRUCTOR sets all the instance variables
     * @param aPos this position's position in the a direction
     * @param bPos this position's position in the b direction
     * @param facingDirection the direction this position is facing in */
    public Position (double aPos, double bPos, double facingDirection) {
        this.aPos = aPos;
        this.bPos = bPos;
        this.facingDirection = facingDirection;
    }
    public Position (Position position) {
        this.aPos = position.aPos;
        this.bPos = position.bPos;
        this.facingDirection = position.facingDirection;
    }
    /** The distance between this point and another point
     * @param other the other point to compare this one to
     * @return the between this point and other */
    public double distanceTo (Position other) {
        return Math.hypot(aPos - other.aPos, bPos - other.bPos);
    }
    /** The angle that would point from this point to another
     * @param other the other point
     * @return the angle that would point from this point to another */
    public double angleTo (Position other) {
        if (aPos > other.aPos) {
            return Math.PI + Math.atan((bPos - other.bPos)/ (aPos - other.aPos));
        } else if (aPos < other.aPos) {
            return Math.atan((bPos - other.bPos)/ (aPos - other.aPos));
        } else if (bPos > other.bPos) {
            return 3 * Math.PI / 2;
        } else {
            return Math.PI / 2;
        }
    }
    @Override
    public String toString() {
        return "(" + shorten(aPos, 6) + "," + shorten(bPos, 6) + ") facing " + shorten(facingDirection, 6);
    }
    /** Creates a string containing the first len characters of a number represented as a string
     * @param value the value to convert to a string
     * @param len the maximum length of the string
     * @return a string containing the first len characters of a number represented as a string*/
    private String shorten (double value, int len) {
        String valStr = "" + value;
        if (valStr.length() < len)
            return valStr;
        else
            return valStr.substring(0, len);
    }
    /** The signed change in this position's facing direction necessary to make it point towards another point. Will always have an absolute value <= PI
     * @param other the point to compare this to
     * @return The signed change in this position's facing direction necessary to make it point towards other. Will always have an absolute value <= PI*/
    public double angleDiff (Position other) {
        double desiredAngle = angleTo(other);
        double diff = desiredAngle - facingDirection;
        if (diff > 0) {
            while (diff > Math.PI) {
                diff -= 2 * Math.PI;
            }
            return diff;
        } else if (diff < 0) {
            while (diff < -1 * Math.PI) {
                diff += 2 * Math.PI;
            }
            return diff;
        } else
            return diff;
    }
    /** Calculates the average of a list of Positions
     * @param positions the Positions to average
     * @return the average of the Positions */
    public static Position avgPos (ArrayList<Position> positions) {
        double aSum = 0.0;
        double bSum = 0.0;
        double sinSum = 0.0;
        double cosSum = 0.0;
        for (Position pos : positions) {
            aSum += pos.aPos;
            bSum += pos.bPos;
            sinSum += Math.sin(pos.facingDirection);
            cosSum += Math.cos(pos.facingDirection);
        }
        double facingDirection = Math.atan2(sinSum / positions.size(), cosSum / positions.size());
        return new Position (aSum / positions.size(), bSum / positions.size(), facingDirection);
    }
    /** Note: changes the angles in positions. CHECK IF THIS IS STILL TRUE */
    public static Position medianPos (ArrayList<Position> positions) {
        ArrayList<Double> aPoses = new ArrayList<>();
        ArrayList<Double> bPoses = new ArrayList<>();
        ArrayList<Double> facings = new ArrayList<>();
        for (Position pos : positions) {
            aPoses.add(pos.aPos);
            bPoses.add(pos.bPos);
            double facing = pos.facingDirection;
            while (facing >= 2 * Math.PI)
                facing -= 2 * Math.PI;
            while (facing < 0)
                facing += 2 * Math.PI;
            facings.add(facing);
        }
        return new Position (medianNum(aPoses), medianNum(bPoses), medianNum(facings));
    }
    /** Note: destroys the nums list. */
    private static double medianNum (ArrayList<Double> nums) {
        ArrayList<Double> numsCopy = new ArrayList<Double>(nums);
        for (int count = 1; count < nums.size() / 2; count ++) {
            numsCopy.remove(lowestNumIndex(nums));
        }
        return numsCopy.get(lowestNumIndex(nums));
    }
    private static int lowestNumIndex (ArrayList<Double> nums) {
        int index = 0;
        double lowest = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) < lowest) {
                lowest = nums.get(i);
                index = i;
            }
        }
        return index;
    }

}
