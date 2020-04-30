package game;
import collidable.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The type Score tracking listener.
 * this is a listener that responsible to update the score counter of the player.
 * according to hitting in blocks.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener - sits on the block.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // 5 points for hitting a block and not exploding him
        if (beingHit.getCount() != 0 && hitter.getPillIdentifier() == null) {
            currentScore.increase(5);
        }
        // 10 points for removing block
        if (beingHit.getCount() == 0 && hitter.getPillIdentifier() == null) {
            currentScore.increase(10);
        }
    }
}
