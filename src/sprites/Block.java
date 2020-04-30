package sprites;
import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import collidable.HitNotifier;
import collidable.Collidable;
import collidable.HitListener;
import collidable.Velocity;

/**
 * Block
 * <p>
 * Class of a Block - our main object in this project.
 * <p>
 * the block implements sprite and collidable interfaces.
 * it also a collidable object and also drawn on the graphic surface.
 * the block will use us to create all the bricks in the game, also we use it
 * at the beginning to create the boarders of game.
 * the block is initialized by upperLeft point and width and height.
 *
 * @author Arye Amsalem
 * @version 3.00 4 April 2019
 */
public class Block implements Collidable, Sprite, HitNotifier {

    // fields
    private Rectangle shape; // the block is a rectangle shaped
    private Color color; // color of the block
    private int count; // the number of hits left to the block until X
    private List<HitListener> hitListeners = new ArrayList<>();
    private String blockOwner; // so we can know if the block is an alien or player
    private Map<Integer, String> hitColors;
    private Color strokeColor;
    private String imgPath;
    private Image currentImg;


    /**
     * Instantiates a new Block.
     *
     * @param rectangle   the rectangle (shape) of block
     * @param count       the count
     * @param colorMap    the color map
     * @param strokeColor the stroke color
     * @param imgPath     the img path
     */
    public Block(Rectangle rectangle, int count, Map<Integer,
            String> colorMap, Color strokeColor, String imgPath) {
        ColorsParser cp = new ColorsParser();
        this.shape = rectangle;

        this.count = count;
        this.hitColors = colorMap;
        this.strokeColor = strokeColor;
        this.imgPath = imgPath;
        this.blockOwner = "game";
        if (imgPath != null) {
            currentImg = General.loadImage(imgPath);
        }
//        if (colorMap.get(1).contains("color") && count == 1) {
//            this.color = cp.colorFromString(colorMap.get(1));
//        }
        if (count >= 1) {
            String colorString = hitColors.get(count);
            if (colorString.contains("color")) {
                this.color = cp.colorFromString(hitColors.get(count));
            }
            if (colorString.contains("image")) {
                currentImg = General.loadImage(hitColors.get(count));
            }
        }
    }

    /**
     * Instantiates a new Block.
     *
     * @param rectangle the rectangle (shape) of block
     * @param color     the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.shape = rectangle;
        this.color = color;
        blockOwner = "game";
    }

    // functions

    /**
     * shape getter - returns the rectangle.
     *
     * @return the rectangle of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Hit -  this function returns the new velocity after collision.
     * it checks whether the collision point is on which one of the rectangle's lines
     * and according to that flips the dx/dy compatibly.
     * it also checks if the collision point was the exact one of 4 rectangle's vertexes - then
     * it flips both of dx/dy.
     *
     * @param hitter          the hitting object
     * @param collisionPoint  the collision point of ball and block
     * @param currentVelocity the current velocity of ball
     * @return the new velocity after collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        int flag = 0; // this flag notify if we already did change in velocity so wont override.
        Velocity newV = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        // all the rectangle's lines
        Point leftUp = this.shape.getUpperLeft();
        Point leftDown = this.shape.getBottomLeft();
        Point rightUp = this.shape.getUpperRight();
        Point rightDown = this.shape.getBottomRight();
        if (hitter.getPillIdentifier() == null && (this.blockOwner.equals("game")
                || this.blockOwner.equals("player") || this.blockOwner.equals("time"))) {

            // hit on one of the rectangle vertexes
            if (collisionPoint.equals(leftUp) || collisionPoint.equals(rightUp)
                    || collisionPoint.equals(leftDown) || collisionPoint.equals(rightDown)) {
                newV.setDy(-currentVelocity.getDy());
                newV.setDx(-currentVelocity.getDx());
                flag = 1;
            }
            // hit only top or bottom - flip dy
            if ((collisionPoint.inLine(this.shape.getUp())
                    || collisionPoint.inLine(this.shape.getBottom())) && flag == 0) {
                newV.setDy(-currentVelocity.getDy());
            }
            // hit only left or right - flip dx
            if ((collisionPoint.inLine(this.shape.getLeft())
                    || collisionPoint.inLine(this.shape.getRight())) && flag == 0) {
                newV.setDx(-currentVelocity.getDx());
            }
            count -= 1; // minus 1 the counter of hits
            updateFill();
        }
        // notify all the block listeners of the hit - this is very important!
        this.notifyHit(hitter);
        // returns the new velocity after hit
        return newV;
    }

    /**
     * Draw on.
     * draws the ball on the given DrawSurface.
     *
     * @param surface is the "canvas".
     */
    public void drawOn(biuoop.DrawSurface surface) {

        // Draw the image on a DrawSurface
        if (currentImg != null) {
            surface.drawImage((int) this.getCollisionRectangle().getUpperLeft().getX(),
                    (int) this.getCollisionRectangle().getUpperLeft().getY(), currentImg);
        }
        if (this.color != null) {
            surface.setColor(this.color); // setting up color of the block
            surface.fillRectangle((int) shape.getUpperLeft().getX(),
                    (int) shape.getUpperLeft().getY(), (int) shape.getWidth(), (int) shape.getHeight());
        }
        if (this.strokeColor != null) {
            surface.setColor(strokeColor); // setting up the block's frame color - black
            surface.drawRectangle((int) shape.getUpperLeft().getX(),
                    (int) shape.getUpperLeft().getY(), (int) shape.getWidth(), (int) shape.getHeight());
        }
    }

    /**
     * timePassed - in this level we do nothing.
     */
    @Override
    public void timePassed() {
        if (blockOwner.equals("time")) {
            this.color = General.getRandomColor();
        }
        //updateFill();
    }

    /**
     * Add to game.
     * this method adds the block to the game collidable and sprite lists.
     *
     * @param g the game object
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove from game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    // Add hl as a listener to hit events.
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    // Remove hl from the list of listeners to hit events.
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * notify hit - method that is responsible to update all the listeners that
     * are registered to the block with the event hit..
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Gets count.
     *
     * @return the count number of block (hits left)
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     * this function sets the hit counter of the block, the reason its not
     * initialized in the constructor is because i want to keep an option to
     * change it during game for later assignments.
     *
     * @param counts the counts
     */
    public void setCount(String counts) {
        this.count = Integer.parseInt(counts); // convert string to int
    }

    /**
     * Gets hit listeners.
     *
     * @return the hit listeners
     */
    public List<HitListener> getHitListeners() {
        return hitListeners;
    }

    /**
     * Gets block owner.
     *
     * @return the block owner
     */
    public String getBlockOwner() {
        return blockOwner;
    }

    /**
     * Sets block owner.
     *
     * @param blockO the block owner
     */
    public void setBlockOwner(String blockO) {
        this.blockOwner = blockO;
    }

    /**
     * Update fill.
     */
    public void updateFill() {
        ColorsParser cp = new ColorsParser();
        if (this.count >= 1 && hitColors != null && hitColors.containsKey(count)) {
            String colorString = hitColors.get(count);
            if (colorString.contains("color")) {
                this.color = cp.colorFromString(hitColors.get(count));
            }
            if (colorString.contains("image")) {
                currentImg = General.loadImage(hitColors.get(count));
            }
        } else if (imgPath != null) {
            // load the image data into an java.awt.Image object
            currentImg = General.loadImage(imgPath);
        }
    }
}