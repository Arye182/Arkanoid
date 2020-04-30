package game;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
import java.awt.Color;

/**
 * The type Countdown animation.
 * this is the animation of counting down 3,2,1 GO!
 * it is a class implements the animation interface, and planned to wait 3 seconds.
 * before every turn.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds; // the amount of time the animation should run (2)
    private int countFrom; // from what second we should countdown from (3)
    private int secLeft; // tracking the seconds number just for printing
    private long timeLeft; // the milliseconds tracker for conditions
    private SpriteCollection spriteCollection; // we need that to draw the game
    private Sleeper sleeper = new Sleeper(); // we should hae a sleeper for waiting

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.spriteCollection = gameScreen;
        this.secLeft = countFrom;
        // i added more 2/3 seconds for printing the GO! word...
        this.timeLeft = (long) ((numOfSeconds * 1000) + (numOfSeconds / countFrom) * 1000);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // draw the game
        spriteCollection.drawAllOn(d);
        d.setColor(Color.white);
        // wait 2/3 seconds
        if (timeLeft != 0 && timeLeft != (long) ((numOfSeconds * 1000) + (numOfSeconds / countFrom) * 1000)) {
            sleeper.sleepFor((long) (1000 * (numOfSeconds / countFrom)));
        }
        // print countdown seconds
        if (timeLeft > (numOfSeconds / countFrom) * 1000) {
            d.drawText(GameLevel.SCREEN_WIDTH / 2 - 55,
                    GameLevel.SCREEN_HEIGHT / 2 + 150, Integer.toString(secLeft), 200);
        }
        // update counters
        secLeft -= 1;
        timeLeft -= (numOfSeconds / countFrom) * 1000;
    }

    @Override
    public boolean shouldStop() {
        // this animation termination condition
        return (timeLeft < 0);
    }
}
