package game;
import geometry.Point;
import geometry.Rectangle;
import sprites.Block;
import sprites.ColorsParser;

import java.awt.Color;
import java.util.Map;

/**
 * The type Block creation.
 * this object implements block creator it is actually a sort of factory
 */
public class BlockCreation implements BlockCreator {

    private String symball;
    private int height;
    private int width;
    private int hitPoints;
    private Color strokeColor;
    private String imgPath;
    private Map<Integer, String> hitColors;

    /**
     * Instantiates a new Block creation.
     *
     * @param symball     the symball
     * @param height      the height
     * @param width       the width
     * @param hitPoints   the hit points
     * @param strokeColor the stroke color
     * @param imgPath     the img path
     * @param hitColors   the hit colors
     */
    public BlockCreation(String symball, int height, int width, int hitPoints,
                         Color strokeColor, String imgPath,  Map<Integer, String> hitColors) {
        this.height = height;
        this.hitPoints = hitPoints;
        this.imgPath = imgPath;
        this.symball = symball;
        this.width = width;
        this.hitColors = hitColors;
        this.strokeColor = strokeColor;
    }
    @Override
    public Block create(int xPos, int yPos) {
        return new Block(new Rectangle(new Point(xPos, yPos), width, height),
                hitPoints, hitColors, strokeColor, imgPath);
    }

    /**
     * Gets fill color.
     *
     * @return the fill color
     */
    public Color getFillColor() {
        ColorsParser cp = new ColorsParser();
        return cp.colorFromString(hitColors.get(1));
    }

    /**
     * Gets stroke color.
     *
     * @return the stroke color
     */
    public Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets symball.
     *
     * @return the symball
     */
    public String getSymball() {
        return symball;
    }
}
