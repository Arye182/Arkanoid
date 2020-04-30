package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import collidable.Velocity;
import collidable.CollisionInfo;
import game.GameEnvironment;

/**
 * Ball
 * <p>
 * Class of a Ball - our main object in this project.
 * <p>
 * the ball will also be a very important object during this assignment and i
 * assume in the future as well.
 * the ball has different kinds of settings such as velocity, radius, center
 * point , color etc.
 * <p>
 * it also has multiple behaviors, setters and getters to allow us control
 * it from whenever we want. the ball implements the sprite interface which allows
 * it to draw itself and notice time passed for drawing.
 *
 * @author Arye Amsalem
 * @version 3.00 24 March 2019
 */
public class Ball implements Sprite {

    // fields
    private Point center;
    private int radius;
    private Color color;
    private Velocity ballVelocity;
    private GameEnvironment gameEnvironment;
    private String pillIdentifier;

    // constructors

    /**
     * Instantiates a new Ball using center as a point , radius and color.
     *
     * @param center the center point of ball.
     * @param r      is the radius of the ball.
     * @param color  the color.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        pillIdentifier = null;
    }

    /**
     * Instantiates a new Ball using x and y values instead of point, the rest
     * like the previous constructor..
     *
     * @param x     is the x coordinate value of center.
     * @param y     is the y coordinate value of center.
     * @param r     is the radius of the ball.
     * @param color is the color.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        // creating new center for ball
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }

    // accessors

    /**
     * getX method.
     *
     * @return the x coordinate value of the center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * getY method.
     *
     * @return the y coordinate value of the center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * getSize method.
     *
     * @return the size of ball - radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * getColor method.
     *
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Gets velocity of the ball.
     *
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.ballVelocity;
    }

    // setters

    /**
     * Sets velocity.
     *
     * @param v is the new velocity to set.
     */
    public void setVelocity(Velocity v) {
        this.ballVelocity = v;
    }

    /**
     * Sets velocity by dx & dy and not Velocity type.
     *
     * @param dx the new dx.
     * @param dy the new dy.
     */
    public void setVelocity(double dx, double dy) {
        this.ballVelocity = new Velocity(dx, dy);
    }

    /**
     * Draw on.
     * draws the ball on the given DrawSurface.
     *
     * @param surface is the "canvas".
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color); // setting up the ball's color!
        surface.fillCircle((int) center.getX(), (int) center.getY(), radius); // fill color
        surface.setColor(Color.black);
        surface.drawCircle((int) center.getX(), (int) center.getY(), radius); // draw circle
    }

// functions

    /**
     * time passed - another implementation of sprite,
     * let the ball move one step..
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Move one step - is a very important method of the ball.
     * this actually controls the flow movement of the ball on the screen.
     * here goes collision detection algorithm:
     * 1. save data before change in velocity
     * 2. apply to point and set after point
     * 3. create trajectory line from before and after
     * 4. scan for collisions in game
     * 5. if there was a collision update after point according to collision
     * point and new velocity.
     * if there was no collision - let the ball move to the after point as usual.
     * NOTE - this algorithm is not suitable for balls with large radius and i
     * intend to improve that for the next assignment so i can play around with
     * the game and it will be more pretty.
     */
    public void moveOneStep() {
        // saving the balls velocity before change
        Velocity beforeV = ballVelocity;
        // saving before center
        Point before = new Point(this.center.getX(), this.center.getY());
        // first thing we do is to let the ball take its next step
        Point after = getVelocity().applyToPoint(center);
        // trajectory is line starts from before apply to point and after
        Line trajectory = new Line(before, after);
        // getting information about closest collision related to trajectory
        CollisionInfo info = gameEnvironment.getClosestCollision(trajectory);
        // save collision point!
        Point collisionPoint = info.getCollisionPoint();
        // offset is the radius + 2 pixels to pull over the after center from collision
        double offset = this.radius + 2;
        // check if there was a collision? then do this!
        if (collisionPoint != null) {
            Velocity newV = info.getCollisionObject().hit(this, collisionPoint, this.ballVelocity);
            this.ballVelocity = newV;
            after = collisionPoint;
            if (this.getPillIdentifier() == null) {
                // hit bottom
                if (newV.getDy() > 0 && beforeV.getDy() < 0) {
                    after.setY(collisionPoint.getY() + offset);
                }
                // hit up
                if (newV.getDy() < 0 && beforeV.getDy() > 0) {
                    after.setY(collisionPoint.getY() - offset);
                }
                // hit left
                if (newV.getDx() > 0 && beforeV.getDx() < 0) {
                    after.setX(collisionPoint.getX() + offset);
                }
                // hit right
                if (newV.getDx() < 0 && beforeV.getDx() > 0) {
                    after.setX(collisionPoint.getX() - offset);
                }
            }
        }
        // in any case there was not a collision it will just move to the after as usual
        this.center = after;
    }

    /**
     * Add the ball to the game - in this case to the sprite list.
     *
     * @param g the game object.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove the ball from the game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Gets pill identifier.
     *
     * @return the pill identifier
     */
    public String getPillIdentifier() {
        return pillIdentifier;
    }

    /**
     * Sets pill identifier.
     *
     * @param identifier the pill identifier
     */
    public void setPillIdentifier(String identifier) {
        this.pillIdentifier = identifier;
    }

    /**
     * Gets ball velocity.
     *
     * @return the ball velocity
     */
    public Velocity getBallVelocity() {
        return ballVelocity;
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets center.
     *
     * @param c the center
     */
    public void setCenter(Point c) {
        this.center = c;
    }

    /**
     * Gets game environment.
     *
     * @return the game environment
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * Sets game enviroment.
     *
     * @param environment the enviroment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }
}