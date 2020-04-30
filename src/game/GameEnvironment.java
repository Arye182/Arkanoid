package game;
import java.util.List;
import java.util.ArrayList;
import geometry.Line;
import geometry.Point;
import collidable.CollisionInfo;
import collidable.Collidable;

/**
 * GameEnvironment
 * <p>
 * the game environment includes all the collidable objects in a list and it
 * also knows how to get the closest collision information according to
 * trajectory.
 *
 * @author Arye Amsalem
 * @version 1.00 4 April 2019
 */
public class GameEnvironment {

    // field - list of collidables
    private List<Collidable> collidables;

    /**
     * Instantiates a new GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Add collidable.
     * add the given collidable to the list!
     *
     * @param c the collidable object
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Remove collidable.
     *
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Gets closest collision.
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * about the closest collision that is going to occur - return null else,
     * if there is a collision we want the collision point and also the specific
     * rectangle on whom the collision occurred.
     *
     * @param trajectory the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // new collision information - at first i initialize it to null
        CollisionInfo result = new CollisionInfo(null, null);
        Point currentP = trajectory.closestIntersectionToStartOfLine(collidables.get(0).getCollisionRectangle());
        List<Collidable> collidablesList = new ArrayList<>(this.collidables);
        // iterating through collidable list and check every one if there is a collision
        for (int i = 0; i < collidablesList.size(); i++) {
            // hitting point with current obstacle
            Point hit = trajectory.closestIntersectionToStartOfLine(collidablesList.get(i).getCollisionRectangle());
            // the rectangle of the obstacle
            Collidable obstacle = collidablesList.get(i);
            // in case no collision with block - continue to next block
            if (hit == null && currentP == null) {
                continue;
            }
            if (hit == null) {
                continue;
            }
            // case current hit point is null and we found a new hit
            if (currentP == null) {
                result.setCollisionPoint(hit);
                result.setCollisionObject(obstacle);
            }
            // if we found closest hit point we update
            if (trajectory.start().distance(hit) <= trajectory.start().distance(currentP)) {
                result.setCollisionPoint(hit);
                result.setCollisionObject(obstacle);
            }
            currentP = hit;
        }
        return result;
    }
}
