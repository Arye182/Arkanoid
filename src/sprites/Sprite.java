package sprites;

/**
 * The interface Sprite.
 * every object that will implement this interface will implement two methods:
 * draw on and timePassed.
 * it helps us control everything that happens during the animation.
 * we would want to draw all objects at once. and let them that time passed so
 * they can move next step or any other different behaviour they want.
 *
 * @author Arye Amsalem
 * @version 1.00 4 April 2019
 */
public interface Sprite {
    /**
     * Draw on.
     * draws the object on the surface.
     *
     * @param d the d
     */
    void drawOn(biuoop.DrawSurface d);

    /**
     * Time passed.
     * let the object know to move one step ahead or do any behaviour he should
     * do, like change color or something.
     */
    void timePassed();
}
