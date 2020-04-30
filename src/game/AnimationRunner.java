package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The type Animation runner.
 * this actually runs every animation we give him
 * it has a GUI and sleeper and the method run gets the animations and
 * runs them.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public class AnimationRunner {
    // fields
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper = new Sleeper();

    /**
     * Instantiates a new Animation runner.
     *
     * @param frames the frames per second
     * @param g      the gui of the animation runner
     */
    AnimationRunner(int frames, GUI g) {
        this.framesPerSecond = frames;
        this.gui = g;
    }

    /**
     * Runs an animation given to this runner.
     *
     * @param animation the animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            // take starting time
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            // draws one frame in the animation
            animation.doOneFrame(d);
            if (animation.shouldStop()) {
                return;
            }
            // show the canvas to the user
            gui.show(d);
            // timing calculation
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
