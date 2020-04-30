package game;
import sprites.Block;
import java.util.Map;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Is space symbol boolean.
     * returns true if 's' is a valid space symbol.
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     * returns true if 's' is a valid block symbol.
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * Gets block.
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     *
     * @param s    the s
     * @param xPos the x pos
     * @param yPos the y pos
     * @return the block
     */
    public Block getBlock(String s, int xPos, int yPos) {
        if (isBlockSymbol(s)) {
            return blockCreators.get(s).create(xPos, yPos);
        }
        return null;
    }

    /**
     * Get space width int.
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s the s
     * @return the int
     */
    public int getSpaceWidth(String s) {
        if (isSpaceSymbol(s)) {
            return this.spacerWidths.get(s);
        }
        return -1;
    }

    /**
     * Gets block creators.
     *
     * @return the block creators
     */
    public Map<String, BlockCreator> getBlockCreators() {
        return blockCreators;
    }

    /**
     * Gets spacer widths.
     *
     * @return the spacer widths
     */
    public Map<String, Integer> getSpacerWidths() {
        return spacerWidths;
    }
}
