package game;
import biuoop.DrawSurface;
import sprites.General;
import java.awt.Color;
import java.awt.Image;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scoresTable;
    private Image backGroundHighScores = General.loadImage("background_images/highscores.png");

    /**
     * Instantiates a new High scores animation.
     *
     * @param scoresTable the scores table
     */
    public HighScoresAnimation(HighScoresTable scoresTable) {
        this.scoresTable = scoresTable;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        // draw back ground of high scores
        if (backGroundHighScores != null) {
            d.drawImage(0, 0, backGroundHighScores);
        } else {
            d.setColor(Color.black);
            d.fillRectangle(0, 0, 800, 600);
        }
        // print names and scores
        if (!scoresTable.getHighScores().isEmpty()) {
            for (int i = 0, y = 220; i < scoresTable.getHighScores().size(); i++, y += 50) {
                if (scoresTable.getHighScores().get(i) == null) {
                    break;
                } else {
                    d.setColor(Color.yellow);
                    d.drawText(320, y, scoresTable.getHighScores().get(i).getName(), 30);
                    d.drawText(550, y, Integer.toString(scoresTable.getHighScores().get(i).getScore()), 30);
                }
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
