package collidable;
import sprites.Block;
import sprites.Ball;

/**
 * The interface Hit listener.
 * when notified the listener will act according to what is written in hit event.
 * if the conditions are satisfied the event will happen.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public interface HitListener {
    /**
     * Hit event.
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    void hitEvent(Block beingHit, Ball hitter);
}
