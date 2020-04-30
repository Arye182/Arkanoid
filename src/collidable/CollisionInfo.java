package collidable;
import geometry.Point;

/**
 * CollisionInfo
 * <p>
 * Class of a CollisionInfo - the collision info gives us the information
 * about two things:
 * 1. the collision point.
 * 2. the collision rectangle.
 * with this information we can calculate things and move the ball in
 * compatibly.
 *
 * @author Arye Amsalem
 * @version 1.00 4 April 2019
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Instantiates a new Collision info - given hit point and collidable object.
     *
     * @param hit      the hit point.
     * @param obstacle the obstacle (collidable).
     */
    public CollisionInfo(Point hit, Collidable obstacle) {
        this.collisionPoint = hit;
        this.collisionObject = obstacle;
    }

    /**
     * Gets collision object.
     *
     * @return the collision object
     */
    public Collidable getCollisionObject() {
        return collisionObject;
    }

    /**
     * Sets collision object.
     *
     * @param cObject the collision object
     */
    public void setCollisionObject(Collidable cObject) {
        this.collisionObject = cObject;
    }

    /**
     * Gets collision point.
     *
     * @return the collision point
     */
    public Point getCollisionPoint() {
        return collisionPoint;
    }

    /**
     * Sets collision point.
     *
     * @param cPoint the collision point
     */
    public void setCollisionPoint(Point cPoint) {
        this.collisionPoint = cPoint;
    }
}
