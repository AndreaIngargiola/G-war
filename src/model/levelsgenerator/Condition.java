package model.levelsgenerator;
import java.util.List;

import model.entities.Component;

/**
 * Condition is an interface that defines the placing conditions of the block, each condition is associated 
 * at a Component, because an entity is formed by Components.
 * @param <X> is the component associated with the list of boolean that represent the placing conditions in the matrix.
 */
public interface Condition<X extends Component> {

    boolean verify(Coordinate c);
}
