package collidable;
import geometry.Rectangle;
import geometry.Point;
import sprites.Ball;

/**
 * The interface Collidable.
 * this interface will be implemented by the collidable objects: block, paddle.
 * a collidable object know how to return its shape (rectangle) and how to
 * return a new velocity after collision.
 *
 * @author Arye Amsalem
 * @version 1.00 4 April 2019
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     * Return the "collision shape" of the object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Hit velocity.
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
