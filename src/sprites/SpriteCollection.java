package sprites;
import java.util.List;
import java.util.ArrayList;

/**
 * SpriteCollection
 * <p>
 * Class of a SpriteCollection - the object that includes all the sprite objects
 * in the game.
 * <p>
 * this sprites collection know to activate them all it can draw all of them on
 * or let all know that time passed.
 * also can add sprites to the list.
 *
 * @author Arye Amsalem
 * @version 2.00 11 May 2019
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     * it is actually a list of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * Add sprite - adds the sprite object to sprite list.
     *
     * @param s the sprite object to add to list
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Remove sprite from the collection.
     *
     * @param s the sprite to be removed
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notify all time passed - notify all the sprites that time passed and they
     * need to  move one step or what the have in the time passed instructions.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : spritesCopy) {
            if (s != null) {
                s.timePassed();
            }
        }
    }

    /**
     * Draw all on - draw every sprite in the game using draw on.
     *
     * @param d the DrawSurface
     */
    public void drawAllOn(biuoop.DrawSurface d) {
        for (Sprite s : sprites) {
            if (s != null) {
                s.drawOn(d);
            }
        }
    }
}
