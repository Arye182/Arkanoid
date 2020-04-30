package collidable;
import geometry.Point;

/**
 * Velocity
 * <p>
 * Class of the velocity of the Ball. Velocity has change in x axis and change
 * in y axis, we mark it dx and dy.
 * the velocity can be changed all time and can be created also from angle
 * and speed and also from dx and dy values.
 *
 * @author Arye Amsalem
 * @version 2.00 24 March 2019
 */
public class Velocity {

    // fields
    private double dx;
    private double dy;

    // constructor

    /**
     * Instantiates a new Velocity by dx and dy.
     *
     * @param dx the new dx.
     * @param dy the new dy.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * From angle and speed velocity this is using trigonometric functions and
     * mathematical basics in trig, so we can calculate and convert angle and
     * power to dx & dy. and by that create new velocity given angle and power.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        // the -1 correction because the demand was up direction - 0 deg.
        double dy = (-1) * speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }

    // accessors

    /**
     * Gets dx of velocity.
     *
     * @return the dx of velocity.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Sets dx.
     *
     * @param newDx the dx
     */
    public void setDx(double newDx) {
        this.dx = newDx;
    }

    /**
     * Gets dy of the velocity.
     *
     * @return the dy of the velocity.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets dy.
     *
     * @param newDy the dy
     */
    public void setDy(double newDy) {
        this.dy = newDy;
    }

    /**
     * Apply to point point.
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     *
     * @param p is the point to update
     * @return the new point with new coordinates.
     */
    public Point applyToPoint(Point p) {
        double newDx = p.getX() + dx;
        double newDy = p.getY() + dy;
        return new Point(newDx, newDy); // creating new point to return
    }
}
