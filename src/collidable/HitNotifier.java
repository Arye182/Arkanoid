package collidable;

/**
 * The interface Hit notifier.
 * the block will implement this interface so we can add and remove listeners
 * from it.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public interface HitNotifier {
    /**
     * Add hit listener.
     * Add hl as a listener to hit events.
     *
     * @param hl the hit listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hit listener.
     * Remove hit listener from the list of listeners to hit events.
     *
     * @param hl the hit listener
     */
    void removeHitListener(HitListener hl);
}
