package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Rectangle
 * <p>
 * Class of Rectangle. it is created by upper left , width and height.
 * the rectangle will use us as part of block and paddle. and also the boarders
 * of game which are also blocks.
 * the rectangle will set himself when created to other useful fields.
 *
 * @author Arye Amsalem
 * @version 1.00 4 April 2019
 */
public class Rectangle {

    // fields
    // all the points
    private Point upperLeft;
    private Point upperRight;
    private Point bottomLeft;
    private Point bottomRight;
    private double width; // the width
    private double height; // the height
    // all the lines ofd rectangle
    private Line up;
    private Line bottom;
    private Line left;
    private Line right;
    // array of lines so it will be more convenient to iterate
    private Line[] rectangle = new Line[4];

    /**
     * Instantiates a new Rectangle.
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        setRectangle(upperLeft, width, height); // setting up all rectangle points and lines
    }

    /**
     * Sets rectangle.
     * this function gets the basic details of the rectangle and uses them to
     * set up all. the rest fields. this function is very usefull - we uses it
     * also when moving the paddle left and right.
     *
     * @param p the upperLeft point of rectangle
     * @param w the width
     * @param h the height
     */
    public void setRectangle(Point p, double w, double h) {
        height = h; // setting up height
        width = w; // setting up weight
        upperLeft = p; // setting up upperLeft Point
        // setting up rest of points
        upperRight = new Point(p.getX() + w, p.getY());
        bottomLeft = new Point(p.getX(), p.getY() + h);
        bottomRight = new Point(p.getX() + w, p.getY() + h);
        // setting up lines
        up = new Line(upperLeft, upperRight);
        bottom = new Line(bottomLeft, bottomRight);
        left = new Line(upperLeft, bottomLeft);
        right = new Line(upperRight, bottomRight);
    }

    /**
     * Intersection points list.
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line this is the trajectory in our case.
     * @return the list with intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        // 4 lines for the rectangle:
        rectangle[0] = up;
        rectangle[1] = bottom;
        rectangle[2] = left;
        rectangle[3] = right;
        // create new list
        List<Point> intersectionPoints = new ArrayList<>();
        // check all rectangle interaction points with line
        for (int i = 0; i < 4; i++) {
            if (line.isIntersecting(rectangle[i])) {
                // if there is an intersection - add it to the list
                intersectionPoints.add(line.intersectionWith(rectangle[i]));
            }
        }
        return intersectionPoints; // returns the list
    }

    /**
     * Gets width.
     * Return the width of the rectangle
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets upper left.
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Gets bottom left.
     *
     * @return the bottom left
     */
    public Point getBottomLeft() {
        return bottomLeft;
    }

    /**
     * Gets bottom right.
     *
     * @return the bottom right
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Gets upper right.
     *
     * @return the upper right
     */
    public Point getUpperRight() {
        return upperRight;
    }

    /**
     * Gets bottom.
     *
     * @return the bottom line
     */
    public Line getBottom() {
        return bottom;
    }

    /**
     * Gets left.
     *
     * @return the left line
     */
    public Line getLeft() {
        return left;
    }

    /**
     * Gets right.
     *
     * @return the right line
     */
    public Line getRight() {
        return right;
    }

    /**
     * Gets up.
     *
     * @return the upper line
     */
    public Line getUp() {
        return up;
    }
}
