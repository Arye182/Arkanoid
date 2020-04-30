package sprites;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import collidable.Velocity;
import collidable.Collidable;
import collidable.HitListener;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
 * The type Paddle
 * <p>
 * Class of a Paddle - our player.
 * <p>
 * the Paddle implements sprite and collidable interfaces.
 * it also a collidable object and also drawn on the graphic surface.
 * but it implements differently - hit is different the paddle top line is
 * divided to 5 regions so it will be more fun to play (like in the original game)
 * and time passed move the paddle left and right according to players keyboard presses.
 *
 * @author Arye Amsalem
 * @version 2.00 11 May 2019
 */
public class Paddle implements Sprite, Collidable {
    // the keyboard sensor of the paddle
    private KeyboardSensor keyboard;
    // the paddle is a rectangle
    private Point paddlePoint;
    private int paddleWidth;
    private int paddleHeight;
    private Block paddle;
    // it has color
    private Color color;
    // it has speed
    private int speed;

    /**
     * Instantiates a new Paddle.
     * i chose to initialize the paddle only like this and with no rectangle settings
     * because in this time i dont allow any other extra paddles. there is only one and
     * its settings are known in advance.
     *
     * @param keyB  the key
     * @param speed the speed
     * @param width the width
     */
    public Paddle(KeyboardSensor keyB, int speed, int width) {
        this.keyboard = keyB;
        this.paddleWidth = width;
        this.paddleHeight = GameLevel.PADDLE_HEIGHT;
        this.speed = speed;
        this.paddlePoint = new Point(GameLevel.SCREEN_WIDTH / 2 - width / 2, GameLevel.SCREEN_HEIGHT - 25);
        this.color = Color.orange;
        this.paddle = new Block(new Rectangle(paddlePoint, width, 0), this.color);
        this.paddle.setBlockOwner("player");
    }

    /**
     * Move left.
     * this function updates the rectangle upper left point of the block
     * with the paddle speed change in case pressed left.
     */
    public void moveLeft() {
        // if player pressed left
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            double newX = paddle.getCollisionRectangle().getUpperLeft().getX() - speed; // change x coordinate
            double newY = paddle.getCollisionRectangle().getUpperLeft().getY();
            if (newX <= GameLevel.BORDER_SIZE) {
                newX = GameLevel.BORDER_SIZE + 1;
            }
            Point newUpLeft = new Point(newX, newY);
            // update new position
            paddle.getCollisionRectangle().setRectangle(newUpLeft, paddleWidth, 0);
        }
    }

    /**
     * Move right.
     * this function updates the rectangle upper left point of the block
     * with the paddle speed change in case pressed left.
     */
    public void moveRight() {
        // if keyboard is pressed right
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            double newX = paddle.getCollisionRectangle().getUpperLeft().getX() + speed; // change x coordinate
            double newY = paddle.getCollisionRectangle().getUpperLeft().getY();
            // if player exceeded right border
            if (newX >= GameLevel.SCREEN_WIDTH - GameLevel.BORDER_SIZE - paddleWidth) {
                newX = GameLevel.SCREEN_WIDTH - GameLevel.BORDER_SIZE - paddleWidth - 1;
            }
            Point newUpLeft = new Point(newX, newY);
            // updating new position
            paddle.getCollisionRectangle().setRectangle(newUpLeft, paddleWidth, 0);
        }
    }

    /**
     * for the paddle - time passed means to move left or right.
     * so it checks if it has to move left or right.
     */
    public void timePassed() {
        moveLeft();
        moveRight();
    }

    /**
     * Draw on the paddle on the draw surface.
     *
     * @param d the DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        // the paddle is a rectangle so fill and draw it
        d.fillRectangle((int) paddle.getCollisionRectangle().getUpperLeft().getX(),
                (int) paddle.getCollisionRectangle().getUpperLeft().getY(),
                (int) paddle.getCollisionRectangle().getWidth(), paddleHeight);
        d.setColor(Color.BLACK); // setting up the ball's color!
        d.drawRectangle((int) paddle.getCollisionRectangle().getUpperLeft().getX(),
                (int) paddle.getCollisionRectangle().getUpperLeft().getY(),
                (int) paddle.getCollisionRectangle().getWidth(), paddleHeight);
    }

    /**
     * Get Collision Rectangle returns the shape of the paddle which is rectangle.
     *
     * @return the rectangle of the paddle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**
     * Hit -  this function returns the new velocity after collision.
     * it checks whether the collision point is on which one of the paddle's lines
     * especially regions - the top line of paddle is divided to 5 regions that
     * send the ball after in different angle.
     *
     * @param hitter          the hitting object
     * @param collisionPoint  the collision point of ball and block
     * @param currentVelocity the current velocity of ball
     * @return the new velocity after collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newV = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        double regionWidth = paddle.getCollisionRectangle().getWidth() / 5;
        double pX = paddle.getCollisionRectangle().getUpperLeft().getX();
        double pY = paddle.getCollisionRectangle().getUpperLeft().getY();
        // setting up the paddle's regions
        Line region1 = new Line(pX, pY, pX + regionWidth, pY);
        Line region2 = new Line(pX + regionWidth, pY, pX + 2 * regionWidth, pY);
        Line region3 = new Line(pX + 2 * regionWidth, pY, pX + 3 * regionWidth, pY);
        Line region4 = new Line(pX + 3 * regionWidth, pY, pX + 4 * regionWidth, pY);
        Line region5 = new Line(pX + 4 * regionWidth, pY, pX + 5 * regionWidth, pY);
        double v = Math.sqrt(newV.getDx() * newV.getDx() + newV.getDy() * newV.getDy());
        // if hit is on region 1
        if (collisionPoint.inLine(region1)) {
            newV = Velocity.fromAngleAndSpeed(300, v);
        }
        // if hit is on region 2
        if (collisionPoint.inLine(region2)) {
            newV = Velocity.fromAngleAndSpeed(330, v);
        }
        // if hit is on region 3
        if (collisionPoint.inLine(region3)) {
            newV.setDy(-currentVelocity.getDy());
        }
        // if hit is on region 4
        if (collisionPoint.inLine(region4)) {
            newV = Velocity.fromAngleAndSpeed(30, v);
        }
        // if hit is on region 5
        if (collisionPoint.inLine(region5)) {
            newV = Velocity.fromAngleAndSpeed(60, v);
        }
        // hit only left or right of paddle
        if ((collisionPoint.inLine(this.paddle.getCollisionRectangle().getLeft())
                || collisionPoint.inLine(this.paddle.getCollisionRectangle().getRight()))) {
            newV.setDx(-currentVelocity.getDx());
        }
        List<HitListener> listeners = this.paddle.getHitListeners();
        List<HitListener> listeners2 = new ArrayList<HitListener>(listeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners2) {
            hl.hitEvent(this.paddle, hitter);
        }
        // returns the new velocity after hit
        return newV;
    }

    /**
     * Add to game.
     * Add this paddle to the game.
     *
     * @param g the game object
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Gets paddle point.
     *
     * @return the paddle point
     */
    public Point getPaddlePoint() {
        return paddlePoint;
    }

    /**
     * Gets paddle width.
     *
     * @return the paddle width
     */
    public int getPaddleWidth() {
        return paddleWidth;
    }

    /**
     * Sets paddle width.
     *
     * @param pw the paddle width
     */
    public void setPaddleWidth(int pw) {
        this.paddleWidth = pw;
    }

    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    public Block getPaddle() {
        return paddle;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets speed.
     *
     * @param s the speed
     */
    public void setSpeed(int s) {
        this.speed = s;
    }

}