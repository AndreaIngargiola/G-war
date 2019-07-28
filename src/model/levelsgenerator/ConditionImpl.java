package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;

import model.entities.Component;
import model.math.Function;

/**
 * ConditionImpl is the implementation of the intefrace Condition that uses the class Function from a 2013 OOP exam.
 * The main private field is a list of Functions that maps a list of coordinates in a boolean. 
 * Each node of the list says if ALL the coordinates respect that function. 
 * @param <X> is the Component associated with this condition.
 */
public class ConditionImpl<X extends Component> implements Condition<X> {

    private final List<Function<Coordinate, Boolean>> mapper;

    /**
     * Initialize the condition.
     * @param startingFunction are the initial functions in the condition.
     */
    public ConditionImpl(final List<Function<Coordinate, Boolean>> startingFunction) {
        this.mapper = new ArrayList<>();
        mapper.addAll(startingFunction);
    }

    @Override
    public final boolean verify(final Coordinate c) {
        return this.mapper.stream()
                          .map(f -> f.apply(c))
                          .allMatch(f -> f.equals(Boolean.FALSE));
    }

    /**
     * Add a function in this condition.
     * @param newFunction is the new function to add.
     */
    public void addCondition(final Function<Coordinate, Boolean> newFunction) {
        mapper.add(newFunction);
    }
}
