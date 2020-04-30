package collidable;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * The type Pill remover.
 * it is a listener that is responsible to remove the pills when they hit either
 * the paddle or the death block.
 *
 * also every pill has its own behavior that takes place in the hit event.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public class PillRemover implements HitListener {
    private GameLevel gameLevel;

    /**
     * Instantiates a new Pill remover - constructor.
     *
     * @param gameLevel is the gameLevel of the ball
     */
    public PillRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getCollisionRectangle().getUpperLeft().getY() == GameLevel.SCREEN_HEIGHT) {
            // it means we have to remove the pill
            hitter.removeFromGame(gameLevel);
            return;
        }
        // different events for each pill :)
        if (hitter.getPillIdentifier() != null && !hitter.getPillIdentifier().equals("bullet")) {
            if (hitter.getPillIdentifier().equals("100")) {
                gameLevel.getScoreCounter().increase(100);
            }
            if (hitter.getPillIdentifier().equals("+1+")) {
                gameLevel.getLivesCounter().increase(1);
            }
            if (hitter.getPillIdentifier().equals("200")) {
                gameLevel.getScoreCounter().increase(200);
            }
            if (hitter.getPillIdentifier().equals("???")) {
                gameLevel.getLivesCounter().decrease(1);
            }
            if (hitter.getPillIdentifier().equals("<->")) {
                gameLevel.getP().getCollisionRectangle().
                        setRectangle(gameLevel.getP().getCollisionRectangle().getUpperLeft(),
                        gameLevel.getP().getPaddleWidth() + 50, 0);
                gameLevel.getP().setPaddleWidth(gameLevel.getP().getPaddleWidth() + 50);
            }
            if (hitter.getPillIdentifier().equals(">-<")) {
                if (gameLevel.getP().getPaddleWidth() > 40) {
                    gameLevel.getP().getCollisionRectangle().
                            setRectangle(gameLevel.getP().getCollisionRectangle().getUpperLeft(),
                            gameLevel.getP().getPaddleWidth() - 50, 0);
                    gameLevel.getP().setPaddleWidth(gameLevel.getP().getPaddleWidth() - 50);
                }
            }
            if (hitter.getPillIdentifier().equals("slw")) {
                if (gameLevel.getP().getSpeed() >= 6) {
                    gameLevel.getP().setSpeed(gameLevel.getP().getSpeed() - 5);
                }
            }
            if (hitter.getPillIdentifier().equals("fst")) {
                gameLevel.getP().setSpeed(gameLevel.getP().getSpeed() + 5);
            }
            if (hitter.getPillIdentifier().equals("o")) {
                gameLevel.createBall();
                gameLevel.getBallsCounter().increase(1);
            }
            if (hitter.getPillIdentifier().equals("oo")) {
                gameLevel.createBall();
                gameLevel.createBall();
                gameLevel.getBallsCounter().increase(2);
            }
            if (hitter.getPillIdentifier().equals("ooo")) {
                gameLevel.createBall();
                gameLevel.createBall();
                gameLevel.createBall();
                gameLevel.getBallsCounter().increase(3);
            }
            hitter.removeFromGame(gameLevel);
        }
        if (hitter.getPillIdentifier() != null && hitter.getPillIdentifier().equals("bullet")) {
            if (beingHit.getBlockOwner().equals("player")) {
                gameLevel.getLivesCounter().decrease(1);
            }
            hitter.removeFromGame(gameLevel);
        }
    }
}
