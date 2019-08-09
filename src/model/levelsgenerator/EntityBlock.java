package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.levelsgenerator.conditions.Condition;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.geometry.BlockImpl;
import model.levelsgenerator.geometry.BlockInsertion;
import model.levelsgenerator.geometry.Coordinate;
import model.levelsgenerator.geometry.GridImpl;
import model.utility.Pair;

/**
 * An extension of the Block class that implements the Entity handler.
 */
public class EntityBlock extends BlockImpl {

    private List<Condition> placingConditions;
    private final LevelGenerationEntity<?> entity;

    /**
     * Initialize a 1x1 block associated with an Entity.
     * @param e is the entity to associate.
     * @param cg is the condition giver for creating the placingCondition looking at the entity components.
     */
    public EntityBlock(final LevelGenerationEntity<?> e, final ConditionGiver cg) {
        super();
        this.entity = e;
        this.buildConditions(cg);
    }

    /**
     * Decipher the component list with the condition giver, initializing the placing conditions.
     * @param componentNames is the list of component interfaces included in the entity.
     * @param cg is the condition giver that associate a condition to a component, if is needed.
     */
    private void buildConditions(final ConditionGiver cg) {
        this.placingConditions = new ArrayList<>();

        /*retrieve the conditions for the component interface, if there aren't, do nothing*/
        for (final String componentName : this.entity.getComponentsSet()) {
            final Optional<List<Condition>> conditions = cg.getConditions(componentName);
            if (!conditions.equals(Optional.empty())) {
                this.placingConditions.addAll(conditions.get());
            }
        }
    }

    /**
     * Get the entity associated with this block.
     * @return the entity associated with this block.
     */
    public LevelGenerationEntity<?> getEntity() {
        return this.entity;
    }

    /**
     * Verify if this block can be placed in the grid at that insertion point.
     * @param gridSnapshot is a snapshot of the grid.
     * @param insertionPoint is the point of the grid that will corresponds to the spawn point of the block.
     * @return true if all conditions are respected, false otherwise.
     */
    public Boolean verifyPlacingConditions(final GridImpl gridSnapshot, final Coordinate insertionPoint) {
        return this.placingConditions.stream()
                              .map(c -> c.verify(new BlockInsertion<GridImpl, EntityBlock, Coordinate>(gridSnapshot, this, insertionPoint)))
                              .allMatch(c -> c.equals(Boolean.TRUE));
    }
}
