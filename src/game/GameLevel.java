package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.Random;
import collidable.Velocity;
import collidable.Collidable;
import collidable.BallRemover;
import collidable.BlockRemover;
import collidable.PillRemover;
import sprites.Sprite;
import sprites.Ball;
import sprites.LevelNameIndicator;
import sprites.LivesIndicator;
import sprites.Block;
import sprites.SpriteCollection;
import sprites.Paddle;
import sprites.Pill;
import sprites.Alien;
import sprites.ScoreIndicator;
import sprites.General;
import geometry.Point;
import geometry.Rectangle;

/**
 * GameLevel
 * <p>
 * Class of a GameLevel - the object that includes all the settings of the game and
 * the game initialization, set and run.
 *
 * @author Arye Amsalem
 * @version 2.00 11 May 2019
 */
public class GameLevel implements Animation {

    // constants (magic numbers)
    public static final int BORDER_SIZE = 25;
    public static final int BLOCK_WIDTH = 50;
    public static final int BLOCK_HEIGHT = 22;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int PADDLE_HEIGHT = 12;

    // game fields
    private LevelInformation info;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private KeyboardSensor keyboard;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter scoreCounter;
    private Counter livesCounter;
    private ScoreTrackingListener scoreTracker;
    private BallRemover ballRemover;
    private BlockRemover blockRemover;
    private ScoreIndicator scoreIndicator;
    private LivesIndicator livesIndicator;
    private LevelNameIndicator levelNameIndicator;
    private Paddle p;
    private AnimationRunner runner;
    private boolean running;
    private Random rand = new Random(); // create a random-number generator

    /**
     * Instantiates a new Game level.
     *
     * @param info         the level info
     * @param k            the keyboard sensor
     * @param ar           the animation runner
     * @param scoreCounter the score counter
     * @param livesCounter the lives counter
     */
    public GameLevel(LevelInformation info, KeyboardSensor k,
                     AnimationRunner ar, Counter scoreCounter, Counter livesCounter) {
        this.info = info;
        this.keyboard = k;
        this.runner = ar;
        this.scoreCounter = scoreCounter;
        this.scoreIndicator = new ScoreIndicator(scoreCounter);
        this.livesCounter = livesCounter;
        this.levelNameIndicator = new LevelNameIndicator(info.levelName());
        this.sprites = new SpriteCollection(); // creating a new sprite collection
        this.environment = new GameEnvironment(); // creating new game environment
        this.livesIndicator = new LivesIndicator(livesCounter);
    }

    /**
     * Add collidable to game environment.
     *
     * @param c the collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Remove collidable.
     *
     * @param c the collidable object
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Add sprite to sprite collection.
     *
     * @param s the sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove sprite.
     *
     * @param s the spite to be removed
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize.
     * Initialize a new game: create the Blocks and Paddle, environment, borders
     * and add them to the game. and also the collections of sprites, and
     * game environment.
     */
    public void initialize() {
        // adding sprites
        sprites.addSprite(info.getBackground());
        sprites.addSprite(new LevelNameIndicator(info.levelName()));
        sprites.addSprite(scoreIndicator);
        sprites.addSprite(livesIndicator);
        // setting up counters
        blocksCounter = new Counter(info.numberOfBlocksToRemove());
        ballsCounter = new Counter(info.numberOfBalls());
        ballRemover = new BallRemover(this, ballsCounter);
        // setting up the blocks
        for (Block block : info.blocks()) {
            blockRemover = new BlockRemover(this, blocksCounter);
            scoreTracker = new ScoreTrackingListener(scoreCounter);
            // setting up the count life of block
            block.addToGame(this); // add block to game
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTracker);
            if (block.getBlockOwner().equals("time")) {
                block.addHitListener(ballRemover);
                block.removeHitListener(blockRemover);
                block.removeHitListener(scoreTracker);
            }
        }
        // setting up the boarders of game
        Color borderColor = new Color(43, 42, 43);
        Block upB = new Block(new Rectangle(new Point(0, BORDER_SIZE), SCREEN_WIDTH, BORDER_SIZE), borderColor);
        Block rightB = new Block(new Rectangle(new Point(SCREEN_WIDTH - BORDER_SIZE, 2 * BORDER_SIZE), BORDER_SIZE,
                SCREEN_HEIGHT - BORDER_SIZE), borderColor);
        Block leftB = new Block(new Rectangle(new Point(0, 2 * BORDER_SIZE), BORDER_SIZE,
                SCREEN_HEIGHT - BORDER_SIZE), borderColor);
        // adding the borders to the game
        upB.addToGame(this);
        leftB.addToGame(this);
        rightB.addToGame(this);
        // setting up death region - when balls hit it they are removed
        Block deathBlock = new Block(new Rectangle(new Point(BORDER_SIZE, SCREEN_HEIGHT),
                SCREEN_WIDTH - BORDER_SIZE, BORDER_SIZE - 10), borderColor);
        environment.addCollidable(deathBlock);
        deathBlock.addHitListener(ballRemover);
        deathBlock.addHitListener(new PillRemover(this));
        // setting up a new paddle
        p = new Paddle(keyboard, info.paddleSpeed(), info.paddleWidth());
        p.addToGame(this);
        p.getPaddle().addHitListener(new PillRemover(this));
        // create alien for specific level
        if (levelNameIndicator.getName().equals("Abstract Space")
                || levelNameIndicator.getName().equals("Retro Games")) {
            Alien alien = new Alien(new Rectangle(new Point(350, 80), 60, 12), Color.orange, this);
            alien.addToGame(this);
            alien.getAlienBlock().addHitListener(new BallRemover(this, this.ballsCounter));
        }
    }

    /**
     * Run the game - start the animation loop.
     * by playing one turn we ask our animation runner to run the game animation.
     * at first we will reset the balls and paddle, then we will run the countdown animation
     * and then run this animation which is the game itself.
     */
    public void playOneTurn() {
        // reset balls and paddle
        this.restartTurn();
        // countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game
        this.runner.run(this);
    }

    @Override
    public boolean shouldStop() {
        // if all the removable blocks were exploded then its time to finish this animation
        if (this.blocksCounter.getValue() == 0) {
            //of course: +100 points for end the level.
            this.scoreCounter.increase(100);
            //this.gui.close(); // close the gui (literally close)
            return true;
        }
        if (this.livesCounter.getValue() == 0) {
            return true;
        }
        // if running out of balls - stop (true)
        return (this.ballsCounter.getValue() == 0);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // notice all sprites to draw on
        this.sprites.drawAllOn(d);
        // time passed - let sprites move one step
        this.sprites.notifyAllTimePassed();
        // feature - pause the game
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
        // this is a very cool feature i wanted to enter :) lets see if you will discover it with no hints
        if (levelNameIndicator.getName().equals("Retro Games") && this.keyboard.isPressed("a")) {
            d.setColor(new Color(132, 49, 35));
            d.fillCircle(515, 475, 12);
            createBall();
            getBallsCounter().increase(1);
        }
    }

    /**
     * Pill creator.
     *
     * @param pillPoint the pill point
     */
    public void pillCreator(Point pillPoint) {
        String[] pills = new String[11];
        pills[0] = "100";
        pills[1] = "+1+";
        pills[2] = "200";
        pills[3] = "???";
        pills[4] = "<->";
        pills[5] = ">-<";
        pills[6] = "slw";
        pills[7] = "fst";
        pills[8] = "o";
        pills[9] = "oo";
        pills[10] = "ooo";
        // generate random x & y coordinates suitable for boundaries of frame
        int x = (int) pillPoint.getX();
        int c = rand.nextInt(11);
        int y = (int) pillPoint.getY();
        Pill newPill = new Pill(new Point(x, y), 8, Color.BLACK, pills[c]);
        newPill.setVelocity(Velocity.fromAngleAndSpeed(180, 2)); // setting up velocity
        newPill.setGameEnvironment(this.environment);
        newPill.addToGame(this);
    }

    /**
     * Restart turn.
     * basically reset our paddle to the center of the screen and
     * re-create our 2 balls.
     */
    public void restartTurn() {
        // setting up balls
        for (int i = 0; i < info.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(GameLevel.SCREEN_WIDTH / 2, 570), 4, Color.white);
            ball.setVelocity(info.initialBallVelocities().get(i)); // setting up velocity
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }
        // paddle
        p.getCollisionRectangle().setRectangle(new Point(SCREEN_WIDTH / 2 - p.getPaddleWidth() / 2,
                GameLevel.SCREEN_HEIGHT - 25), p.getPaddleWidth(), 0);
    }

    /**
     * Gets blocks counter.
     *
     * @return the blocks counter
     */
    public Counter getBlocksCounter() {
        return blocksCounter;
    }

    /**
     * Gets lives counter.
     *
     * @return the lives counter
     */
    public Counter getLivesCounter() {
        return livesCounter;
    }

    /**
     * Gets balls counter.
     *
     * @return the balls counter
     */
    public Counter getBallsCounter() {
        return ballsCounter;
    }

    /**
     * Gets score counter.
     *
     * @return the score counter
     */
    public Counter getScoreCounter() {
        return scoreCounter;
    }

    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    public Paddle getP() {
        return p;
    }

    /**
     * Throw pills.
     *
     * @param pillPoint the pill point
     */
    public void throwPills(Point pillPoint) {
        int num = rand.nextInt(400);
        if (num >= 50 && num <= 100) {
            pillCreator(pillPoint);
        }

    }

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Gets level information.
     *
     * @return the information of the level
     */
    public LevelInformation getInfo() {
        return info;
    }

    /**
     * create random ball with random color angle and speed.
     */
    public void createBall() {
        Ball ball = new Ball(new Point(400, 150), 4, General.getRandomColor());
        ball.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(330) + 130,
                rand.nextInt(8) + 4)); // setting up velocity
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);
    }
}
