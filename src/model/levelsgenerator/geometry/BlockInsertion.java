package model.levelsgenerator.geometry;

/**
 * Is a wrapper created for code clarity that contains all the informations for the block insertion event.
 * This class is very useful for eventual condition evaluation.
 * @param <X> is an implementation of Grid.
 * @param <Y> is an implementation of Block.
 * @param <Z> is a Coordinate or an extension of it.
 */
public class BlockInsertion<X extends Grid, Y extends Block, Z extends Coordinate> {

    private final X context;
    private final Y block;
    private final Z insertionPoint;

    /**
     * A public constructor for this wrapper.
     * @param context is the grid.
     * @param block is the block.
     * @param insertionPoint is the insertion point in the grid of the block.
     */
    public BlockInsertion(final X context, final Y block, final Z insertionPoint) {
        this.context = context;
        this.block = block;
        this.insertionPoint = insertionPoint;
    }

    /**
     * A getter for the grid.
     * @return the snapshot of the grid.
     */
    public X getContext() {
        return this.context;
    }

    /**
     * A getter for the block.
     * @return the block.
     */
    public Y getBlock() {
        return this.block;
    }

    /**
     * a getter for the insertion point.
     * @return the insertion point.
     */
    public Z getInsertionPoint() {
        return this.insertionPoint;
    }
}
