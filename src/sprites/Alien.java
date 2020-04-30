package sprites;
import collidable.Velocity;
import game.Counter;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;
import java.util.Random;

/**
 * The type Alien.
 * well this is an alien class that will represent a weird space ship in the top
 * of the screen.
 *
 * this alien is a sort of a block that moves.
 * this block is swallowing balls.
 * this alien can shoot too.
 * try to find out when!
 *
 * basically i use inheritance here....so that's an implementation of that issue.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public class Alien extends Block {
    private Velocity alienVelocity;
    private geometry.Point uperLeft = super.getCollisionRectangle().getUpperLeft();
    private Rectangle alienRec = super.getCollisionRectangle();
    private Block alienBlock;
    private double width = super.getCollisionRectangle().getWidth();
    private double height = super.getCollisionRectangle().getHeight();
    private Random rand = new Random(); // create a random-number generator
    // aliens lives counter
    private Counter alienLives;
    private GameLevel game;

    /**
     * Instantiates a new Alien.
     *
     * @param rectangle the rectangle (shape) of block
     * @param color     the color of the block
     * @param g         the g
     */
    public Alien(Rectangle rectangle, Color color, GameLevel g) {
        super(rectangle, color);
        alienVelocity = Velocity.fromAngleAndSpeed(90, 3);
        alienBlock = new Block(alienRec, color);
        alienLives = new Counter(3);
        game = g;
        alienBlock.setBlockOwner("alien");
    }

    @Override
    public void drawOn(biuoop.DrawSurface surface) {
        Color randomColor = General.getRandomColor();
        surface.setColor(Color.green);
        surface.fillCircle((int) (uperLeft.getX() + (this.width / 2)), (int) uperLeft.getY(), (int) height);
        surface.setColor(Color.black); // setting up color of the block
        surface.drawCircle((int) (uperLeft.getX() + (this.width / 2)), (int) uperLeft.getY(), (int) height);
        surface.fillRectangle((int) uperLeft.getX(), (int) uperLeft.getY(), (int) width, (int) height);
        surface.setColor(Color.BLACK); // setting up the block's frame color - black
        surface.drawRectangle((int) uperLeft.getX(), (int) uperLeft.getY(), (int) width, (int) height);
        surface.setColor(Color.DARK_GRAY); // setting up the block's frame color - black
        surface.fillRectangle((int) (uperLeft.getX() + (width / 4)),
                (int) (uperLeft.getY() + height), (int) (width / 2), (int) (height / 2));
        surface.setColor(Color.black); // setting up the block's frame color - black
        surface.drawRectangle((int) (uperLeft.getX() + (width / 4)),
                (int) (uperLeft.getY() + height), (int) (width / 2), (int) (height / 2));
        surface.setColor(randomColor);
        surface.fillCircle((int) (uperLeft.getX() + (1 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
        surface.fillCircle((int) (uperLeft.getX() + (2 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
        surface.fillCircle((int) (uperLeft.getX() + (3 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
        surface.fillCircle((int) (uperLeft.getX() + (4 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
        surface.fillCircle((int) (uperLeft.getX() + (5 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
        surface.fillCircle((int) (uperLeft.getX() + (6 * width / 7)),
                (int) (uperLeft.getY() + (height / 2)), (int) (height / 4));
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Move one step.
     */
    public void moveOneStep() {
        // move
        uperLeft.setX(uperLeft.getX() + alienVelocity.getDx());
        super.getCollisionRectangle().setRectangle(new Point(uperLeft.getX(), uperLeft.getY()), width, height);
        if (alienVelocity.getDx() > 0) {
            if (super.getCollisionRectangle().getUpperRight().getX()
                    >= GameLevel.SCREEN_WIDTH - GameLevel.BORDER_SIZE) {
                alienVelocity.setDx(alienVelocity.getDx() * -1);
            }
        }
        if (alienVelocity.getDx() < 0) {
            if (super.getCollisionRectangle().getUpperLeft().getX() <= GameLevel.BORDER_SIZE) {
                alienVelocity.setDx(alienVelocity.getDx() * -1);
            }
        }
        if (game.getInfo().levelName().equals("Retro Games")) {
            shoot();
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return alienRec;
    }

    /**
     * Gets alien block.
     *
     * @return the alien block
     */
    public Block getAlienBlock() {
        return alienBlock;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this.alienBlock);
    }

    /**
     * Shoot.
     */
    public void shoot() {
        if (alienLives.getValue() != 0) {
            int willIShoot = rand.nextInt(1500);
            if (willIShoot >= 50 && willIShoot <= 55) {
                Bullet bullet = new Bullet(new Point(this.uperLeft.getX() + this.width / 2,
                        this.uperLeft.getY() + this.height + 2), 4, Color.YELLOW, "bullet");
                bullet.setVelocity(Velocity.fromAngleAndSpeed(180, 2)); // setting up velocity
                bullet.setGameEnvironment(game.getEnvironment());
                bullet.addToGame(game);
            }
        }
    }
}
