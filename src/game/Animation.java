package game;
import biuoop.DrawSurface;

/**
 * The interface Animation. this will allow us to separate the animations and
 * play them on the animation runner. so i will implement a pause screen and
 * 3 2 1 GO screen.
 * who will implement this interface will have to implement what happens in
 * a frame of the animation and when the animation should stop.
 *
 * @author Arye Amsalem
 * @version 1.00 11 May 2019
 */
public interface Animation {
    /**
     * Do one frame. (draw it on surface).
     * what happens during one frame of the animation.
     *
     * @param d the draw surface to draw on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Should stop boolean.
     * true if its time to stop the animation, and false if not.
     *
     * @return the boolean param
     */
    boolean shouldStop();
}
