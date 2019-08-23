package model.levelsgenerator.conditions;
import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.geometry.BlockInsertion;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.math.Function;

/**
 * Condition is an interface that defines the placing conditions of the block, each condition is associated 
 * at a Component, because an entity is formed by Components.
 */
public interface Condition {

    /**
     * Add a new function to verify for block placement.
     * @param newFunction is a function that project a Block and a Context (the grid) in a Boolean, 
     * telling what are the context conditions in which the block can be placed.
     */
    void addCondition(Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean> newFunction);

    /**
     * Verify if a block and a context (a snapshot of the grid in which the program want to place the block) respects all the functions.
     * @param c is a BlockInsertion composed by a snapshot of the grid, a block and the insertion point of the block.
     * @return true if the block respects all the conditions, false otherwise.
     */
    boolean verify(BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate> c); 
}
