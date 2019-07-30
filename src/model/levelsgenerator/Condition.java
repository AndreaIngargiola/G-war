package model.levelsgenerator;
import model.components.AbstractComponent;
import model.math.Function;
import model.utility.Pair;


/**
 * Condition is an interface that defines the placing conditions of the block, each condition is associated 
 * at a Component, because an entity is formed by Components.
 * @param <X> is the component associated with the list of boolean that represent the placing conditions in the matrix.
 */
public interface Condition<X extends AbstractComponent> {

    void addCondition(Function<Pair<EntityBlock<?>, ? extends GridImpl>, Boolean> newFunction);

    boolean verify(Pair<EntityBlock<?>, ? extends GridImpl> c); 
}
