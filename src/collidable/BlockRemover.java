package collidable;
import game.GameLevel;
import game.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The type Block remover.
 * a BlockRemover is in charge of removing blocks from the gameLevel,
 * as well as keeping count of the number of blocks that remain. Blocks that are
 * hit and reach 0 hit-points should be removed from the gameLevel.
 * it works with the help of events notify listen pattern.
 *
 * @author Arye Amsalem
 * @version 2.00 28 May 2019
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks; // how much blocks left in the gameLevel

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel     is the gameLevel the block belongs to
     * @param removedBlocks the removed blocks counter
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // it is very important to remove blocks only if regular balls hit them.
        if (beingHit.getCount() == 0 && hitter.getPillIdentifier() == null) {
            // remove the block that got hit
            gameLevel.removeSprite(beingHit);
            gameLevel.removeCollidable(beingHit);
            // important to remove also the listener after the block is removed
            beingHit.removeHitListener(this);
            // update remaining blocks counter
            remainingBlocks.decrease(1);
            gameLevel.throwPills(beingHit.getCollisionRectangle().getUpperLeft());
        }
    }

    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks counter
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }
}
