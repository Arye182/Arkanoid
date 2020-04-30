package game;

import collidable.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.util.List;
import java.util.Map;

/**
 * The type Level information generator.
 */
public class LevelInformationGenerator implements LevelInformation {
    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite getBackground;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;

    /**
     * Instantiates a new Level information generator.
     *
     * @param numberOfBalls          the number of balls
     * @param initialBallVelocities  the initial ball velocities
     * @param paddle                 the paddle
     * @param levelName              the level name
     * @param getBackground          the get background
     * @param blocks                 the blocks
     * @param numberOfBlocksToRemove the number of blocks to remove
     */

    public LevelInformationGenerator(int numberOfBalls, List<Velocity> initialBallVelocities,
                                     Map<String, Integer> paddle, String levelName, Sprite getBackground,
                                     List<Block> blocks, int numberOfBlocksToRemove) {
        this.numberOfBalls = numberOfBalls;
        this.initialBallVelocities = initialBallVelocities;
        this.paddleSpeed = paddle.get("speed");
        this.paddleWidth = paddle.get("width");
        this.levelName = levelName;
        this.getBackground = getBackground;
        this.blocks = blocks;
        this.numberOfBlocksToRemove = numberOfBlocksToRemove;
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return this.getBackground;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
}
