package sprites;
import game.GameLevel;
import game.Counter;
import java.awt.Color;

/**
 * The Score indicator.
 * holds a counter that indicates the players score, its a sprite so we can
 * draw it on the canvas.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Instantiates a new Score indicator.
     *
     * @param score the score counter
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(biuoop.DrawSurface d) {
        d.setColor(Color.black);
        // draws the score value on the canvas
        d.drawText(GameLevel.SCREEN_WIDTH / 2 - 50, GameLevel.BORDER_SIZE - 5, "Score: " + score.getValue(), 18);
    }

    @Override
    public void timePassed() {
        // do nothing
    }
}
