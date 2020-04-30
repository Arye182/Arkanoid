package geometry;

/**
 * Point
 * <p>
 * Class of a Point in the program, points will be used as objects in our
 * drawing window. point can be part of a line or ball (as center).
 *
 * @author Arye Amsalem
 * @version 2.00 04 April 2019
 */
public class Point {

    // the fields of the Point - x & y coordinates.
    private double x;
    private double y;

    /**
     * this is the constructor of the point.
     *
     * @param x is the x coordinate.
     * @param y is the y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * this method calculates the distance between current point
     * and other point.
     *
     * @param other is another point to measure distance with.
     * @return the distance after calculation.
     */
    public double distance(Point other) {
        double x1, x2, y1, y2;
        // calculating distance between two points formula
        if (other != null) {
            x1 = other.x; // x coordinate of other point
            y1 = other.y; // y coordinate of other point
            x2 = this.x; // this points x coordinate
            y2 = this.y; // this point y coordinate
            return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
        } else {
            return -1;
        }
    }

    /**
     * this method checks equality between two points - current and another.
     * there might be epsilon difference due to double \ float values compare.
     * this calculation respect epsilon differences - i think it is important
     * to take precise calculations.
     * Math.abs is used to make no difference if its a negative number.
     *
     * @param other is another point to check if equals to current point.
     * @return the factorial result after recursion.
     */
    public boolean equals(Point other) {
        double epsilon = 1e-9; // "safe side" definition of a threshold
        double deltaX = Math.abs(other.x - this.x); // calculating deltaX
        double deltaY = Math.abs(other.y - this.y); // calculating deltaY
        return ((deltaX < epsilon) && (deltaY < epsilon)); // return answer
    }

    /**
     * this method returns the x coordinate of the point.
     *
     * @return the x coordinate of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * this is an x coordinate setter.
     *
     * @param newX is the new x coordinate.
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * this method returns the y coordinate of the point.
     *
     * @return the y coordinate of this point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * this is a y coordinate setter.
     *
     * @param newY is the new y coordinate.
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * this method checks if the point is on a given line.
     * it uses distances sums to check. if a point is on a line it means that
     * the distance from line start to point + line end to point equals to
     * line distance.
     * we take epsilon check for double equality in java.
     *
     * @param line is the line.
     * @return true or false if the point is on the line.
     */
    public boolean inLine(Line line) {
        double epsilon = 1e-9; // epsilon for accuracy
        double lineDist = line.start().distance(line.end()); // the whole line distance
        double startToPointDist = line.start().distance(this); // distance from start line to point
        double endToPointDist = line.end().distance(this); // distance from end line to point
        return (Math.abs(lineDist - (startToPointDist + endToPointDist)) <= epsilon); // final check
    }
}
