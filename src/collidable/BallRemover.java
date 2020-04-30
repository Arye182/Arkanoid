package collidable;
import game.GameLevel;
import game.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The type Ball remover.
 * this class ball remover is responsible to remove ball when the ball collide
 * the death block in the bottom of the gameLevel screen.
 * it works same as the block remover - with the pattern of event listener
 * notifier.
 * it implements the HitListener interface so we can actually remove the ball
 * in the hit Event - just like the block.
 *
 * @author Arye Amsalem
 * @version 2.00 28 May 2019
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls; // how much balls left in the gameLevel

    /**
     * Instantiates a new Ball remover - constructor.
     *
     * @param gameLevel    is the gameLevel of the ball
     * @param removedBalls the removed balls counter
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove the ball
        hitter.removeFromGame(gameLevel);
        // and update the remaining balls counter (pill identity is due the
        // fact that pill and bullets are balls. and regular ball has null in this field.
        if (hitter.getPillIdentifier() == null) {
            remainingBalls.decrease(1);
        }
        // i created a special block that is a time portal... creates new ball.
        if (beingHit.getBlockOwner().equals("time")) {
            remainingBalls.increase(1);
            gameLevel.createBall();
        }
    }
}
