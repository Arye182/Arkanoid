package sprites;
import game.GameLevel;
import game.Counter;
import java.awt.Color;

/**
 * The type Lives indicator is a sprite that just drawn as text on the canvas.
 * it draws the counter value ov lives left in the game.
 * this time - red hearts.
 *
 * @author Arye Amsalem
 * @version 2.00 28 May 2019
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    // heart as lives :)
    private int[][] livesMatrix = new int[][]{
            {2, 2, 1, 1, 1, 2, 2, 2, 1, 1, 1, 2, 2},
            {2, 1, 0, 0, 0, 1, 2, 1, 0, 0, 0, 1, 2},
            {1, 0, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2},
            {2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2},
            {2, 2, 2, 1, 0, 0, 0, 0, 0, 1, 2, 2, 2},
            {2, 2, 2, 2, 1, 0, 0, 0, 1, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2},
    };
    private Color[] liveColors = new Color[3];

    /**
     * Instantiates a new Lives indicator.
     *
     * @param lives the score counter
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    @Override
    public void drawOn(biuoop.DrawSurface d) {
        d.setColor(Color.black);
        // prints the lives left in the game
        d.drawText(10, GameLevel.BORDER_SIZE - 5, "Lives:", 18);
        liveColors[0] = new Color(250, 0, 0);
        liveColors[1] = new Color(0, 0, 0);
        liveColors[2] = new Color(231, 231, 227);
        int x = 70, y = GameLevel.BORDER_SIZE - 24;
        for (int l = 0; l < lives.getValue(); l++) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 13; j++) {
                    d.setColor(liveColors[livesMatrix[i][j]]);
                    d.fillRectangle(x, y, 2, 2);
                    //d.setColor(Color.green);
                    //d.drawRectangle(x, y, 20, 20 );
                    x += 2;
                }
                y += 2;
                x -= 26;
            }
            y = GameLevel.BORDER_SIZE - 24;
            x += 30;
        }
    }

    @Override
    public void timePassed() {
    }
}
