package sprites;
import biuoop.DrawSurface;
import game.GameLevel;
import java.awt.Color;

/**
 * The type Level name indicator.
 */
public class LevelNameIndicator implements Sprite {
    private String name;

    /**
     * Instantiates a new Level name i ndicator.
     *
     * @param name the name
     */
    public LevelNameIndicator(String name) {
        this.name = name;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        // draws the score value on the canvas
        d.drawText(GameLevel.SCREEN_WIDTH / 2 + 135, GameLevel.BORDER_SIZE - 5, "Level Name: " + name, 18);
    }

    @Override
    public void timePassed() {

    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
