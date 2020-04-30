package game;

import sprites.Block;

/**
 * The interface Block creator.
 * who implements this needs to be able to create block.
 */
public interface BlockCreator {
    /**
     * Create block at the specified location.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    Block create(int xpos, int ypos);
}
