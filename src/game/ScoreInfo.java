package game;

import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets name.
     *
     * @param n the name
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * Sets score.
     *
     * @param s the score
     */
    public void setScore(int s) {
        this.score = s;
    }
}
