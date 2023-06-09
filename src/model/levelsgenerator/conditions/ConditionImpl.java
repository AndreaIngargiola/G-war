package model.levelsgenerator.conditions;

import java.util.ArrayList;
import java.util.List;

import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.geometry.BlockInsertion;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.Grid;
import model.math.Function;

/**
 * ConditionImpl is the implementation of the interface Condition that uses the class Function from a 2013 OOP exam.
 * The main private field is a list of Functions that maps a list of coordinates in a boolean. 
 * Each node of the list says if ALL the coordinates respect that function. 
 */
public class ConditionImpl implements Condition {

    private final List<Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean>> mapper;

    /**
     * Initialize the condition list.
     */
    public ConditionImpl() {
        this.mapper = new ArrayList<>();
    }

    @Override
    public final boolean verify(final BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate> c) {
        return this.mapper.stream()
                          .map(f -> f.apply(c))
                          .allMatch(f -> f.equals(Boolean.TRUE));
    }

    @Override
    /**
     * Add a function in this condition.
     * @param newFunction is the new function to add.
     */
    public void addCondition(final Function<BlockInsertion<? extends Grid, ? extends EntityBlock, ? extends Coordinate>, Boolean> newFunction) {
        mapper.add(newFunction);
    }
}
