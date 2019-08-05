package model.levelsgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import model.levelsgenerator.conditions.Condition;
import model.levelsgenerator.conditions.ConditionGiver;
import model.levelsgenerator.geometry.BlockImpl;

public class EntityBlock extends BlockImpl {

    private List<Condition> placingConditions;
    private final LevelGenerationEntity<?> entity;

    public EntityBlock(final LevelGenerationEntity<?> e, ConditionGiver cg) {
        super();
        this.entity = e;
        this.BuildConditions(e.getComponentsSet(), cg);
    }

    private void BuildConditions(final Set<String> componentNames, ConditionGiver cg) {
        this.placingConditions = new ArrayList<>();

        /*retrieve the conditions for the component interface, if there aren't, do nothing*/
        for (String componentName : this.entity.getComponentsSet()) {
            final Optional<List<Condition>> conditions = cg.getConditions(componentName);
            if (!conditions.equals(Optional.empty())) {
                this.placingConditions.addAll(conditions.get());
            }
        }
    }

    public LevelGenerationEntity getEntity() {
        return this.entity;
    }

}
