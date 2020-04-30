package sprites;
import biuoop.DrawSurface;
import collidable.CollisionInfo;
import collidable.Velocity;
import geometry.Line;
import geometry.Point;
import java.awt.Color;

/**
 * The type Pill. pill is a ball. and behave as same. i just change move one step and draw on.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public class Pill extends Ball {
    /**
     * Instantiates a new Pill.
     *
     * @param center         the center
     * @param r              the r
     * @param color          the color
     * @param pillIdentifier the pill identifier
     */
    public Pill(Point center, int r, Color color, String pillIdentifier) {
        super(center, r, color);
        super.setPillIdentifier(pillIdentifier);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(super.getColor());
        d.fillRectangle(super.getX() - super.getSize(),
                super.getY() - super.getSize(), 2 * super.getSize(), 2 * super.getSize());
        d.fillCircle(super.getX() - super.getSize(), super.getY(), super.getSize());
        d.fillCircle(super.getX() + super.getSize(), super.getY(), super.getSize());
        d.setColor(Color.green);
        d.drawText(super.getX() - 12, super.getY() + 6, super.getPillIdentifier(), 15);
    }

    @Override
    public void moveOneStep() {
        // saving the balls velocity before change
        Velocity beforeV = super.getBallVelocity();
        // saving before center
        Point before = new Point(super.getX(), super.getY());
        // first thing we do is to let the ball take its next step
        Point after = getVelocity().applyToPoint(super.getCenter());
        // trajectory is line starts from before apply to point and after
        Line trajectory = new Line(before, after);
        // getting information about closest collision related to trajectory
        CollisionInfo info = super.getGameEnvironment().getClosestCollision(trajectory);
        // save collision point!
        Point collisionPoint = info.getCollisionPoint();
        // offset is the radius + 2 pixels to pull over the after center from collision
        double offset = super.getSize() + 2;
        if (collisionPoint != null) {
            // check if there was a collision? then do this!
            Velocity newV = info.getCollisionObject().hit(this, collisionPoint, super.getBallVelocity());
            super.setVelocity(newV);
        }
        // in any case there was not a collision it will just move to the after as usual
        super.setCenter(after);
    }
}
