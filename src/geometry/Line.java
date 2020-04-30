package geometry;

import java.util.List;

/**
 * Line
 * <p>
 * Class that represent a Line in our project. the line has start and end point
 * and also have different useful methods like checking if there is
 * intersection with another line or calculating length of line.
 *
 * @author Arye Amsalem
 * @version 2.00 04 April 2019
 */
public class Line {

    // fields
    private Point start;
    private Point end;

    // constructors

    /**
     * constructor for line (by points).
     *
     * @param start is the starting point of the line.
     * @param end   is the ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * line constructor - by coordinates.
     *
     * @param x1 is start point x coordinate.
     * @param y1 is start point y coordinate.
     * @param x2 is end point x coordinate.
     * @param y2 is end point y coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {
        // creating 2 new points for start and end.
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // accessors

    /**
     * length method - calculates line's length.
     *
     * @return the length of the line.
     */
    public double length() {
        return (this.start.distance(end));
    }

    /**
     * middle method - calculates line's middle point.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * start getter - returns line's starting point.
     *
     * @return the starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * end getter - returns line's ending point.
     *
     * @return the ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * slope method - returns line's slope.
     * the calculation of slope is delta Y / delta X;
     *
     * @return the slope of the line.
     */
    public double getSlope() {
        double x1, x2, y1, y2;
        x1 = this.start.getX();
        x2 = this.end.getX();
        y1 = this.start.getY();
        y2 = this.end.getY();
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * this method calculate the b parameter of linear line.
     * the calculation is based on the formulas:
     * y = ax +b.
     * b = y1 - ax1.
     *
     * @return the b parameter..
     */
    public double getB() {
        return (this.end.getY() - (this.getSlope() * this.end.getX()));
    }

    // functionality

    /**
     * in this method i use other method to see if there is an approved
     * intersection point. if so - it means True - intersecting!
     * else - it means that Null has returned and there is no intersection.
     * this method is using the method intersectionWith because the calculation
     * is based on the same tests. so in order to avoid code duplication
     * this is the way i chose to implement this method.
     *
     * @param other - is the line to check with!
     * @return True or False if lines intersect.
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * in order to see if there is an intersecting point between two line
     * segments i am using this mathematics geometric basics:
     * given two lines in a plane:
     * y1 = m1x + b1.
     * y2 = m2x + b2.
     * X intersection = (b2-b1)/*(m1-m2).
     * Y intersection = y1 or y2 (X intersection).
     * <p>
     * i am assuming that there exists an intersection point between the two
     * segments. assuming that, i know how this point will look like according
     * the different cases.
     * <p>
     * eventually i check whether the intersection point is in the range of
     * the two line segments or not. if so - then it is an intersection point
     * and it will be returned, else, the method will return null.
     *
     * @param other is another line to check with.
     * @return the intersecting point of the line else - null.
     */
    public Point intersectionWith(Line other) {
        // first thing to do is setting up parameters for easy work
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();
        double m1; // this line's slope
        double m2; // other line's slope
        double b1; // this line's "b" parameter
        double b2; // other line's "b" parameter
        double interX = 0; // x coordinate of intersection point
        double interY = 0; // y coordinate of intersection point
        // case - line is a point
        if (this.start.equals(this.end)) {
            // two lines are points
            if (other.start.equals(other.end)) {
                return (this.start.equals(other.start) ? start : null);
            } else {
                // other is line so we check if this point is in this line
                return (this.start.inLine(other) ? start : null);
            }
        }
        // case - only other line is point
        if (other.start.equals(other.end)) {
            return (other.start.inLine(this) ? other.start : null);
        }
        // case - two verticals
        if ((x1 == x2) && (x3 == x4)) {
            if (x1 != x3) {
                return null;
            }
            interX = x1;
            // "inRange" checks if the first value is in between the two other
            if (inRange(y1, y4, y3)) {
                interY = y1;
                return new Point(interX, interY);
            }
            if (inRange(y2, y4, y3)) {
                interY = y2;
                return new Point(interX, interY);
            } else {
                return null;
            }
        }
        // case - only this line is vertical
        if ((x1 == x2) && (x3 != x4)) {
            interX = x1;
            interY = (other.getSlope() * interX) + other.getB();
            // creating new point as intersection point
            Point interPoint = new Point(interX, interY);
            // final check to see if the intersection point is on the lines range.
            return ((interPoint.inLine(other) && interPoint.inLine(this)) ? interPoint : null);
        }
        // case - only other line is vertical
        if ((x1 != x2) && (x3 == x4)) {
            interX = x3;
            interY = (this.getSlope() * interX) + this.getB();
            // creating new point as intersection point
            Point interPoint = new Point(interX, interY);
            // final check to see if the intersection point is on the lines range.
            return ((interPoint.inLine(this) && interPoint.inLine(other)) ? interPoint : null);
        }
        // calculating slopes - in case we dont have verticals!
        m1 = this.getSlope();
        m2 = other.getSlope();
        // calculating b factor (y = ax +b)
        b1 = this.getB();
        b2 = other.getB();
        // case of parallels
        if (m1 == m2) {
            // in case the two lines are horizontals
            if (m1 == 0) {
                interY = y2;
                if (inRange(x1, x4, x3)) {
                    interX = x1;
                }
                if (inRange(x2, x4, x3)) {
                    interX = x2;
                }
            } else {
                if (inRange(x1, x4, x3)) {
                    interX = x1;
                }
                if (inRange(x2, x4, x3)) {
                    interX = x2;
                }
                interY = (m1 * interX) + b1;
            }
        }
        // general case
        if (m1 != m2) {
            interX = (b2 - b1) / (m1 - m2); // comparing two linear equations
            interY = (m1 * interX) + b1; // plugin x
        }
        // creating new point as intersection point
        Point interPoint = new Point(interX, interY);
        // final check to see if the intersection point is on the lines range.
        return ((interPoint.inLine(this) && interPoint.inLine(other)) ? interPoint : null);
    }

    /**
     * this method gets 3 values and returns true or false if on is in range of
     * the two others.
     *
     * @param a1 is the value to check in range of a2 and a3.
     * @param a2 is second value in comparison.
     * @param a3 is third value in comparison.
     * @return true or false.
     */
    public boolean inRange(double a1, double a2, double a3) {
        return ((a1 <= a2) && (a1 >= a3));
    }

    /**
     * this method checks equality between lines.
     * either both start and end points are equal or one start is the other end
     * and vice versa.
     *
     * @param other is another line to check with.
     * @return true or false if lines are equal.
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start)));
    }

    /**
     * Closest intersection to start of line point.
     * If this line does not intersect with the rectangle, it returns null.
     * Otherwise, returns the closest intersection point to the
     * start of the line.
     *
     * @param rect the rectangle to check
     * @return the point of intersection (closest)
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        double minSegment;
        // list of intersections with rectangle
        List<Point> intersections = rect.intersectionPoints(this);
        // case the list is empty
        if (intersections.size() == 0) {
            return null;
            // list is noo empty - there is at least one intersection point
        } else {
            // setting up the first point as the minimal
            minSegment = this.start.distance(intersections.get(0));
            Point close = intersections.get(0);
            // if there is a smaller distance in this list i want to update minSegment
            for (int i = 0; i < intersections.size(); i++) {
                double newSegment = this.start.distance(intersections.get(i));
                if (newSegment < minSegment) {
                    minSegment = newSegment;
                    close = intersections.get(i);
                }
            }
            // return the point which has the minimum distance
            return close;
        }
    }
}
