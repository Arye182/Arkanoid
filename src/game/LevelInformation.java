package game;
import collidable.Velocity;
import sprites.Block;
import sprites.Sprite;
import java.util.List;

/**
 * The interface Level information.
 * this interface will be implemented by levels classes and in the classes the
 * level info and details will be found.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public interface LevelInformation {
    /**
     * Number of balls in the level.
     *
     * @return the number of balls in the level
     */
    int numberOfBalls();

    /**
     * Initial ball velocities list.
     *
     * @return the list with The initial velocity of each ball
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed int.
     *
     * @return the paddle speed
     */
    int paddleSpeed();

    /**
     * Paddle width int.
     *
     * @return the paddle width
     */
    int paddleWidth();

    /**
     * Level name string.
     *
     * @return the level name which will be displayed at the top of the screen.
     */
    String levelName();

    /**
     * Gets background.
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * Blocks list.
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return the list of blocks
     */
    List<Block> blocks();

    /**
     * Number of blocks to remove int.
     * Number of levels that should be removed
     * before the level is considered to be "cleared".
     *
     * @return the number of blocks to remove.
     */
    int numberOfBlocksToRemove();
}
