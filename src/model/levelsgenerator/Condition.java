package model.levelsgenerator;
import model.math.Function;
import model.utility.Pair;


/**
 * Condition is an interface that defines the placing conditions of the block, each condition is associated 
 * at a Component, because an entity is formed by Components.
 * @param <X> is the component associated with the list of boolean that represent the placing conditions in the matrix.
 */
public interface Condition {

    /**
     * add a new function to verify for block placement.
     * @param newFunction is a function that project a Block and a Context (the grid) in a Boolean, 
     * telling what are the context conditions in which the block can be placed.
     */
    void addCondition(Function<Pair<EntityBlock, ? extends GridImpl>, Boolean> newFunction);

    /**
     * verify if a block and a context (a snapshot of the grid in which the program want to place the block) respects all the functions.
     * @param c is a pair composed by a block and a snapshot of the grid.
     * @return true if the block respects all the conditions, false otherwise.
     */
    boolean verify(Pair<EntityBlock, ? extends GridImpl> c); 
}
