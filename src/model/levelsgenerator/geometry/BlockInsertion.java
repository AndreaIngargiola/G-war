package model.levelsgenerator.geometry;

/**
 * Is a wrapper created for code clarity that contains all the informations for the block insertion event.
 * This class is very useful for eventual condition evaluation.
 */
public class BlockInsertion {

    private Grid context;
    private Block block;
    private Coordinate insertionPoint;

    /**
     * A public constructor for this wrapper.
     * @param context is the grid.
     * @param block is the block.
     * @param insertionPoint is the insertion point in the grid of the block.
     */
    public BlockInsertion(final Grid context, final Block block, final Coordinate insertionPoint) {
        super();
        this.context = context;
        this.block = block;
        this.insertionPoint = insertionPoint;
    }

    /**
     * A getter for the grid.
     * @return the snapshot of the grid.
     */
    public Grid getContext() {
        return context;
    }

    /**
     * A getter for the block.
     * @return the block.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * a getter for the insertion point.
     * @return the insertion point.
     */
    public Coordinate getInsertionPoint() {
        return insertionPoint;
    }
}
