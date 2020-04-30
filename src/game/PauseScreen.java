package game;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Pause screen.
 * this is an animation of the pause screen its just in case we want to pause
 * the game.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class PauseScreen implements Animation {
    @Override
    public void doOneFrame(DrawSurface d) {
        // print on the surface the massage of pause
        d.setColor(new Color(69, 69, 69));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(18, 22, 100));
        d.fillCircle(400, 250, 180);
        d.setColor(new Color(239, 239, 239));
        d.fillRectangle(305, 170, 90, 180);
        d.fillRectangle(405, 170, 90, 180);
        d.setColor(new Color(140, 234, 51));
        d.drawText(92, 550, "Paused -- Press Space To Continue", 40);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
