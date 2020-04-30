package sprites;
import biuoop.DrawSurface;
import geometry.Point;
import java.awt.Color;

/**
 * The type Bullet.
 *
 * @author Arye Amsalem
 * @version 1.00 28 May 2019
 */
public class Bullet extends Pill {
    /**
     * Instantiates a new Bullet.
     *
     * @param center         the center
     * @param r              the r
     * @param color          the color
     * @param pillIdentifier the pill identifier
     */
    public Bullet(Point center, int r, Color color, String pillIdentifier) {
        super(center, r, color, pillIdentifier);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(super.getColor());
        d.fillOval(super.getX() - super.getSize(), super.getY() - 2 * super.getSize(), super.getSize(),
                3 * super.getSize());
        d.setColor(Color.red);
        d.drawOval(super.getX() - super.getSize(), super.getY() - 2 * super.getSize(), super.getSize(),
                3 * super.getSize());
    }
}
